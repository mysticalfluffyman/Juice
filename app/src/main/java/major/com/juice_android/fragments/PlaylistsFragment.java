package major.com.juice_android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import major.com.juice_android.MainActivity;
import major.com.juice_android.R;
import major.com.juice_android.SettingActivity;
import major.com.juice_android.UserProfileActivity;

public class PlaylistsFragment extends Fragment
{
    Button goToSettingsButton, goToUserProfileButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_playlists, container, false);
        ((MainActivity)getActivity()).setActionBarTitle("Playlist");

        goToSettingsButton = (Button)view.findViewById(R.id.goToSettingsButton);
        goToUserProfileButton = (Button)view.findViewById(R.id.goToUserProfileButton);

        goToSettingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent);
            }
        });

        goToUserProfileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}
