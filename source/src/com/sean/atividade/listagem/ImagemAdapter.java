package com.sean.atividade.listagem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import static com.sean.util.Arquivo.carregaImagem;

import com.sean.R;

public class ImagemAdapter extends BaseAdapter {
	
	private Context contexto;
	private Bitmap[] imagens;
	Bitmap placeholder;
	
	public ImagemAdapter(Context contexto) {
	    this.contexto = contexto;
	    imagens  = new Bitmap[10];
	    placeholder = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.ic_launcher);
	}

	public int getCount() {
	    return imagens.length;
	}
	
	public Object getItem(int posicao) {
	    return posicao;
	}

	public long getItemId(int posicao) {
	    return posicao;
	}

	public View getView(int posicao, View convertView, ViewGroup pai) {
	    ImageView imageView = new ImageView(contexto);
	    imageView.setImageBitmap(imagens[posicao]);
	    imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
	    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	    imageView.setImageDrawable(carregaImagem(contexto, "teste.png"));
	    return imageView;
	}
}
