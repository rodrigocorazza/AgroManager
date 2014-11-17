package com.android.agroconsulta.praga;

import java.util.List;
import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.modelo.Praga;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PragaListAdapter extends BaseAdapter {
	private Context context;
	private List<Praga> lista;
	
	public PragaListAdapter(Context context, List<Praga> lista) {
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
		// Recupera a insumo da posição atual 
		Praga p = lista.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.form_listadapter_linhatabela, null);
		// Atualiza o valor do TextView 
		TextView nome = (TextView) view.findViewById(R.id.nome);
		nome.setText(p.nome);
		TextView tipo = (TextView) view.findViewById(R.id.quantidade);
		tipo.setText(p.tipo);
		return view; 
		} 
	}
	


