package com.android.agroconsulta.propriedaderural;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.modelo.PropriedadeRural;

public class PropriedadeRuralListAdapter extends BaseAdapter {
	private Context context;
	private List<PropriedadeRural> lista;
	
	public PropriedadeRuralListAdapter(Context context, List<PropriedadeRural> lista) {
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
		// Recupera a pessoa da posição atual 
		PropriedadeRural p = lista.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.form_listadapter_linhatabela, null);
		// Atualiza o valor do TextView 
		TextView nome = (TextView) view.findViewById(R.id.nome);
		nome.setText(p.nome);
		TextView tamanho = (TextView) view.findViewById(R.id.quantidade);
		tamanho.setText(String.valueOf(p.tamanho));
		TextView cultura = (TextView) view.findViewById(R.id.cultura);
		cultura.setText(p.cultura);
		return view; } 
	}
	