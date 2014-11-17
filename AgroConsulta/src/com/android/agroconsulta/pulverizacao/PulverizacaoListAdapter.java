package com.android.agroconsulta.pulverizacao;

import java.util.List;

import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.modelo.Pulverizacao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class PulverizacaoListAdapter extends BaseAdapter {

		private Context context;
		private List<Pulverizacao> lista;
		
		public PulverizacaoListAdapter(Context context, List<Pulverizacao> lista) {
			this.context = context;
			this.lista = lista;
			} 
		public int getCount() {
			return lista.size();
			} 
		
		public Object getItem(int position) { 
			return lista.get(position);
			} 
		
		public long getItemId(int position) {
			return position; 
			} 
		
		@SuppressLint({ "ViewHolder", "InflateParams" }) 
		public View getView(int position, View convertView, ViewGroup parent) {
			
			// Recupera a Pulverizacao da posição atual 
			Pulverizacao p = lista.get(position);
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.form_listadapter_pulverizacao, null);
			
			// Atualiza o valor do TextView 
			TextView id = (TextView) view.findViewById(R.id.id);
			id.setText(String.valueOf(p.id));
			
			TextView id_propriedaderural = (TextView) view.findViewById(R.id.id_propriedaderural);
			id_propriedaderural.setText(String.valueOf(p.id_propriedaderural));
			
			TextView id_insumo = (TextView) view.findViewById(R.id.id_insumo);
			id_insumo.setText(String.valueOf(p.id_insumo));
			
			TextView raio = (TextView) view.findViewById(R.id.raio);
			raio.setText(String.valueOf(p.raio));
			
			TextView dataCriacao = (TextView) view.findViewById(R.id.dataCriacao);
			dataCriacao.setText(String.valueOf(p.dataCriacao));
			
			return view; 
			} 
		}
		




