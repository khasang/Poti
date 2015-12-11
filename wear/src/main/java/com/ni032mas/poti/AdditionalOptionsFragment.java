package com.ni032mas.poti;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class AdditionalOptionsFragment extends Fragment {
    App app;
    AppData appData;
    private final int NAME_POSITION = 0;
    private final int COLOR_POSITION = 1;
    private final int VIBRATION_POSITION = 2;
    private final int CICLE_POSITION = 3;
    private static final int SPEECH_REQUEST_CODE = 0;
    private AdditionalOptionsWearableListViewAdapter adapter;

    public static AdditionalOptionsFragment newInstance() {
        AdditionalOptionsFragment fragment = new AdditionalOptionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        appData = app.appData;
        View view = inflater.inflate(R.layout.additional_options_fragment, container, false);
        WearableListView wearableListView = (WearableListView) view.findViewById(R.id.wlv_additional_options);
        adapter = new AdditionalOptionsWearableListViewAdapter(inflater, getActivity());
        wearableListView.setAdapter(adapter);
        wearableListView.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                switch (position) {
                    case NAME_POSITION:
                        displaySpeechRecognizer();
                        break;
                    case COLOR_POSITION:
                        break;
                    case VIBRATION_POSITION:
                        break;
                    case CICLE_POSITION:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });
        return view;
    }

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == -1) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            appData.getLastTimer().setName(spokenText);
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
