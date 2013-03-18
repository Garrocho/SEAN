package com.sean.atividade.listagem;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import static com.sean.util.Arquivo.carregaDrawable;

import com.sean.R;

public class ImagemAdapter extends BaseAdapter {
	
	private Context contexto;
	private Bitmap[] imagens;
	private List<String> endImagens;
	Bitmap placeholder;
	
	public ImagemAdapter(Context contexto, List<String> endImagens) {
	    this.contexto = contexto;
	    imagens  = new Bitmap[endImagens.size()];
	    this.endImagens = endImagens;
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
	    imageView.setImageDrawable(carregaDrawable(contexto, endImagens.get(posicao)));
	    return imageView;
	}
}
