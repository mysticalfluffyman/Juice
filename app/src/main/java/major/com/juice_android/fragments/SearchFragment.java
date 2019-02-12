package major.com.juice_android.fragments;

import  android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import major.com.juice_android.MainActivity;
import major.com.juice_android.R;

public class SearchFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ((MainActivity)getActivity()).setActionBarTitle("Search");

        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
