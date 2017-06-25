package fdi.ucm.ifarmamobile;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import fdi.ucm.Propiedades;


public class MapaFragment extends Fragment{
    public static MapaFragment newInstance() {
        MapaFragment fragment = new MapaFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        String URL = "http://container.fdi.ucm.es:20007/mapa";
        double lat = 40.416942;
        double lon = -3.703545;
        Location location = Propiedades.getInstance().getLocation();
        WebView wv;
        if (location !=null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                wv = (WebView) view.findViewById(R.id.vistaMapa);
                wv.getSettings().setJavaScriptEnabled(true);
                String localizacion = URL + "?lat=" + lat + "&lon=" + lon;
                wv.loadUrl(localizacion);
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return false;
                    }
                });
            } else {
                wv = (WebView) view.findViewById(R.id.vistaMapa);
                wv.getSettings().setJavaScriptEnabled(true);
                String localizacion = URL + "?lat=" + lat + "&lon=" + lon;
                wv.loadUrl(localizacion);
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return false;
                    }
                });
            }
        return view;
    }
}
