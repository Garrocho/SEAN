package com.sean.atividade.galeria;

import static com.sean.util.Arquivo.carregaDrawable;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sean.R;
import com.sean.classe.Imagem;

public class ImagemAdapter extends BaseAdapter {
	
	private Context contexto;
	private Bitmap[] imagens;
	private List<Imagem> endImagens;
	int GalItemBg;

	public ImagemAdapter(Context contexto, List<Imagem> endImagens) {
	    this.contexto = contexto;
	    imagens  = new Bitmap[endImagens.size()];
	    this.endImagens = endImagens;
	    TypedArray typArray = contexto.obtainStyledAttributes(R.styleable.GalleryTheme);
        GalItemBg = typArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
        typArray.recycle();
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
	    imageView.setImageDrawable(carregaDrawable(contexto, endImagens.get(posicao).getEndImagem()));
	    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	    imageView.setBackgroundResource(GalItemBg);
	    return imageView;
	}
}
