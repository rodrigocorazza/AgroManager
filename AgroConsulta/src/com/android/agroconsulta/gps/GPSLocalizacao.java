package com.android.agroconsulta.gps;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.dao.Repositorio;
import com.android.agroconsulta.dao.RepositorioScript;
import com.android.agroconsulta.modelo.Percurso;
import com.android.agroconsulta.modelo.Ponto;
import com.android.agroconsulta.modelo.Pulverizacao;
import com.android.agroconsulta.pulverizacao.RelatorioPulverizacao;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
 
public class GPSLocalizacao extends FragmentActivity {
    	private SupportMapFragment mapFrag;
    	private GoogleMap map;
		private LocationManager mLocalizacao;
		private HashMap<Long,Polyline> polyline;
		private HashMap<Long,List<LatLng>> list;
		private PolylineOptions polylineOptions;
	    private Button btnAddPraga = null;
	    private ToggleButton tgIniciarPausar = null;
	    private Button btnFinalizar = null;
	    private LocationListener locationListener= null;
	    private Location location;
	    private Percurso percurso;
	    public static Repositorio repositorio;
	    private Long idPulverizacao = null;
		private double raio;
		private Long numPercurso;
		
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_pulverizacao_mapa_gps);
	        
	        idPulverizacao = getIntent().getExtras().getLong("pulverizacao");
	        raio = getIntent().getExtras().getDouble("raio");
	        
	        list = new HashMap<Long, List<LatLng>>();
		   	polyline = new HashMap<Long, Polyline>();
		   	
			repositorio = new RepositorioScript(this);
	        
	        btnAddPraga = (Button) findViewById(R.id.btnAddPraga);
	        btnAddPraga.setOnClickListener(new OnClickListener() {
			
				public void onClick(View view) {
					chamaAddPragaMap();
				}});
	        
	        tgIniciarPausar = (ToggleButton) findViewById(R.id.tgIniciarPausar);
	        tgIniciarPausar.setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                if (tgIniciarPausar.isChecked()) {
	                	//ativaGPS();
	                	criaPercurso();
	                	drawRoute();
	        	    } 
	                else {
	                	if(mLocalizacao != null)
	                		mLocalizacao.removeUpdates(locationListener);
	                	encerraPercurso();
	                }
	            }
	        });
	        
	        btnFinalizar = (Button) findViewById(R.id.btnFinalizar);
	        btnFinalizar.setOnClickListener(new OnClickListener() {
			
				public void onClick(View view) {
					mLocalizacao.removeUpdates(locationListener);
                	chamaEncerraPulverizacao();
				}});
	      		        
		    GoogleMapOptions options = new GoogleMapOptions();
	        options.zOrderOnTop(true);
	        
	        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
	        map = mapFrag.getMap();
	     
		}

//==================================ENCERRAR PULVERIZACAO======================
		
		public void chamaEncerraPulverizacao(){
			encerraPercurso();
			
			Intent intent = new Intent(this, RelatorioPulverizacao.class);
        	startActivity(intent);
        }

//===================================ADICIONAR PRAGA===========================		
		
        public void chamaAddPragaMap(){
        	//passando os valores de latitude e longitude junto com a intent
        	Intent intent = new Intent(this, AddPragaMap.class);
			intent.putExtra("latitude", location.getLatitude());
			intent.putExtra("longitude", location.getLongitude());
        	startActivity(intent);
		}
		
//=====================================PERCURSO=================================
		
		
		// criar percurso definindo a data de criação
		public void criaPercurso(){
			
			percurso = new Percurso();
			percurso.dataCriacao = new Date().getTime();
			Pulverizacao pulverizacao = new Pulverizacao();
			pulverizacao.id = idPulverizacao;
			percurso.id_pulverizacao = pulverizacao;
			percurso.id = repositorio.salvaPercurso(percurso);
			
			ativaGPS();
			
			Log.i("CRIAR PERCURSO", "NUMERO PERCURSO: " + percurso.id);
		
		}
		// encerra percurso definindo a data de encerramento 
		public void encerraPercurso(){
			percurso.dataEncerramento = new Date().getTime();	
			repositorio.atualizarPercurso(percurso);
			percurso = null;
		}
		
//===========================================================================================
		
		public void ativaGPS (){
        	
	        locationListener = new LocationListener() {
				
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {
				}
				
				@Override
				public void onProviderEnabled(String provider) {
					Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_LONG).show();
				}
				
				@Override
				public void onProviderDisabled(String provider) {
					Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_LONG ).show();
				}
				
				@Override
				public void onLocationChanged(Location location) {
					novoPonto(location, null);
				}
			};
			
			float distanciaAtualiza = (float) (raio/2);
        	mLocalizacao = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        mLocalizacao.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, distanciaAtualiza,locationListener);
        }
        

 //============================================Gerar ponto ============================================
	    
	   public void novoPonto(Location location, View view){
	    	this.location =location;
		    Ponto ponto = new Ponto();
	    	ponto.data = new Date().getTime();
	    	ponto.latitude = location.getLatitude();
	    	ponto.longitude = location.getLongitude();
	    	ponto.percurso = percurso;
	    	
	    	repositorio.salvaPonto(ponto);
	    	  	
	    	Log.i("PONTO DO PERCURSO", "NUMERO PERCURSO: " + ponto.percurso.id);
	    	
	    	//calculaGeometria(location, ponto);
			
			
		//}

	  // private void calculaGeometria(Location location, Ponto ponto) {
		   //salvo
		   //atualiza base de dados
		   LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude()); //valores latitude, longitude
		   Double lon = latlng.longitude;
		   Double lat = latlng.latitude;
		
		   //definindo o valor da diferença do ponto local para o ponto da pulverizacao 
		   //(multiplica o raio * o valor do grau decimal igual a 1 metro)
		   Double lonRaio = raio * 0.00001;
		   Double latRaio = raio * 0.00001;
		   Log.i("RAIO MULTIPLICADO", "RAIO LON VALOR: " + lonRaio + "\nRAIO LAT VALOR: " + latRaio);
	
		   //Soma o valor da LatLng local mais o valor da diferença 
		   Double lonN = lon + lonRaio;
		   Double latT = lat + latRaio;
		   LatLng latlngRAIO = new LatLng(latT,lonN);
	
		   //o raio eh dividido por 2 para marcar exatamente a posicao da pulverizacao e definir o tamanho de deslocamento do usuario
		   Double raioPulv = raio / 2;
	
		   //Circulo em volta do raio do ponto da pulverizacao
		   map.addCircle(new CircleOptions()
		   	.center(new LatLng(latT,lonN))
		   	.fillColor(Color.argb(100,0,0,255))
		   	.strokeColor(Color.GREEN)
		   	.radius(raioPulv));
	
		/*   map.addMarker(new MarkerOptions()
		   	.position(latlngRAIO));
		   	Log.i("POSICAO PONTO PULV", "Latitude: " + latT + "\nLongitude: " + lonN);
		   	//.title("Evento: "+date.toString()));
		*/
		   //distancia entre os 2 ponto (Ponto atual e pontoRAIO)
		   double distance = 0;
		   distance += distance(latlng, latlngRAIO);
		   Toast.makeText(getApplicationContext(), "Distancia\nPonto - Pulverização: " + distance + " metros", Toast.LENGTH_LONG).show();
		   Log.i("DISTANCE", "PontoLocal E PontoRaio: " + distance + " metros");
	
		   //distancia entre 2 ponto ATUAL
		   for( int i = 0, tam = list.size(); i < tam; i++) {
			   if(i < tam - 1) {
				   distance += distance(list.get(percurso.id).get(i), list.get(percurso.id).get(i+1));

				   Toast.makeText(getApplicationContext(), "Distancia: " + distance + "metros", Toast.LENGTH_LONG).show();
				   //Log.i("DISTANCE", "Pontos Locais: " + distance + "metros");
			   }
		   }
	
		   list.get(percurso.id).add(latlng);
	
		   configCamera(latlng);
		   //criarMarcador(latlng, ponto);
		   drawPolyline();
		   drawCircle(latlng);
		   Toast.makeText(getApplicationContext(), "Novo Ponto Encontrado \n- Latitude: " + ponto.latitude + "\n- Longitude: " + ponto.longitude, Toast.LENGTH_LONG).show();
}

//============================================Pegar Distancia entre os pontos===========================
	   
//	   public void getDistance (View view){
//		   distance += distance(LatLng, latlngRAIO);
//	   }
//	   
	   public static double distance(LatLng StartP, LatLng EndP) {
		    double lat1 = StartP.latitude;
		    double lat2 = EndP.latitude;
		    double lon1 = StartP.longitude;
		    double lon2 = EndP.longitude;
		    double dLat = Math.toRadians(lat2-lat1);
		    double dLon = Math.toRadians(lon2-lon1);
		    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
		    Math.sin(dLon/2) * Math.sin(dLon/2);
		    double c = 2 * Math.asin(Math.sqrt(a));
		    return 6366000 * c;
		}
	   
//============================================desenhar raio ============================================
	    
	    public void drawCircle(LatLng latlng){ //desenhar raio 
	    	
	    	map.addCircle(new CircleOptions()
	         .center(new LatLng(latlng.latitude,latlng.longitude))
	         .fillColor(Color.argb(100,0,0,255))
	         .strokeColor(Color.TRANSPARENT)
	         .radius(raio));
	    }
	    
//============================================desenhar rota ============================================	   
	    
	    public void addPointRoute(LatLng latlng){ //adiciona os pontos latitude e longitude em uma lista
	  		polylineOptions.add(latlng);
	  	}
	    
	    public void drawPolyline(){  //desenha às linhas
	    	polyline.get(percurso.id).setPoints(list.get(percurso.id));
	    }
	    
	    public void removePolyline(){
	     	polyline.get(percurso.id).remove();
	    }
	    
	    public void drawRoute(){  //função para desenhar a linha entre pontos
	         list.put(percurso.id, new ArrayList<LatLng>());
	      	
	    	if(polyline.get(percurso.id) == null){
	    		polylineOptions = new PolylineOptions();
	    		
	    		for(int i = 0, tam = list.get(percurso.id).size(); i< tam; i++){
    				addPointRoute(list.get(percurso.id).get(i)); //chamando a função para adicionar o ponto na list
	    		}	
	    		polylineOptions.color(Color.RED);
	    		polyline.put(percurso.id, map.addPolyline(polylineOptions));
	    	}
//	    	else{
//	    		if( percurso.id == numPercurso){
	    			drawPolyline(); //desenhar a linha nos pontos das list
//	    		}else {
//	    			numPercurso = percurso.id;
//	    			removePolyline();
//    			}
//	    		
//	    	}
	    }	

//============================================ Criar marcador ============================================
	    
	 /*   public void criarMarcador(LatLng latlng, Ponto ponto ){ // criar um marcador no ponto(evento)
	    	ponto.data = new Date().getTime();
	    	map.addMarker(new MarkerOptions()
	        .position(latlng)
	        .title("Evento: "+ new Date() ));
	    }
	 */   
//============================================ Criar Mapa e definir posicionamento ============================================
	    public void configCamera(LatLng latlng){

		    map = mapFrag.getMap();
	    	map.setMapType(GoogleMap.MAP_TYPE_HYBRID); //define o tipo do mapa

	    	//Camera position: coloca os valores no mapa de forma mais completa
	    	//Camera Update: coloca os valores no mapa e entra com paramentros de entrada no mapa
	    	//target: posicionamento
	    	//build: constroi o objeto
	    	//bearing: rotacao
	    	//tilt: inclinacao	   
	    	CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(20).bearing(0).tilt(0).build(); //
	    	CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
	    	
	    	//map.moveCamera(update); // mostrando o posicionamento no mapa
	    	map.animateCamera(update, 3000, new CancelableCallback() {; //faz animacao para mostrar o posicionamento no mapa
	    		@Override
	    		public void onCancel() {
	    			Log.i("Script", "CancelableCalback.onCancel()");
	    		}
	    		public void onFinish() {
	    			Log.i("Script", "CancelableCalback.onFinish()");
	    		}
	    	}); 
    	}
	    
	    @Override
	    public void onResume(){
	    	super.onResume();
	    	
	    }
	    
	    
	    
	    
	    
	    

}