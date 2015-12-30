package io.khasang.poti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdditionalOptionsFragment extends Fragment {
    AppData appData;
    static final int NAME_POSITION = 0;
    static final int COLOR_POSITION = 1;
    static final int VIBRATION_POSITION = 2;
    static final int CYCLE_POSITION = 3;
    private static final int SPEECH_REQUEST_CODE = 0;
    private AdditionalOptionsWearableListViewAdapter adapter;

    public static AdditionalOptionsFragment newInstance() {
        AdditionalOptionsFragment fragment;
        fragment = new AdditionalOptionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        appData = AppData.getInstance(getActivity().getApplicationContext());
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
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_layout, ColorFragment.newInstance())
                                .commit();
                        break;
                    case VIBRATION_POSITION:
                        Intent intent = new Intent(getActivity().getApplicationContext(), VibrateActivity.class);
                        startActivity(intent);
                        break;
                    case CYCLE_POSITION:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });
        wearableListView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
            @Override
            public void onCentralPositionChanged(int i) {
                appData.positionAdditionalOptions = i;
            }
        });
        wearableListView.scrollToPosition(appData.positionAdditionalOptions);
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

    @Override
    public void onResume() {
        super.onResume();

    }
}
