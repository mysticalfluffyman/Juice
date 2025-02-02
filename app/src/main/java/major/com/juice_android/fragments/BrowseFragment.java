package major.com.juice_android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import major.com.juice_android.GenreListActivity;
import major.com.juice_android.MainActivity;
import major.com.juice_android.R;

public class BrowseFragment extends Fragment {
    CardView metal,ninety,rock,folk,classical,hiphop,electronic;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Browse");
        return inflater.inflate(R.layout.fragment_browse, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        metal=view.findViewById(R.id.metal_Browse);
        ninety=view.findViewById(R.id.ninety_Browse);
        rock=view.findViewById(R.id.rock_Browse);
        folk=view.findViewById(R.id.folk_Browse);
        classical=view.findViewById(R.id.classical_Browse);
        hiphop=view.findViewById(R.id.hiphop_Browse);
        electronic=view.findViewById(R.id.electronic_Browse);


        metal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GenreListActivity.class);
                intent.putExtra("genreid", "2");
                startActivity(intent);
            }
        });
        ninety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GenreListActivity.class);
                intent.putExtra("genreid", "6");
                startActivity(intent);
            }
        });
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), GenreListActivity.class);
                intent.putExtra("genreid", "1");
                startActivity(intent);
            }
        });
        classical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GenreListActivity.class);
                intent.putExtra("genreid", "7");
                startActivity(intent);
            }
        });
        hiphop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GenreListActivity.class);
                intent.putExtra("genreid", "3");
                startActivity(intent);
            }
        });
        folk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GenreListActivity.class);
                intent.putExtra("genreid", "2");
                startActivity(intent);
            }
        });
        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GenreListActivity.class);
                intent.putExtra("genreid", "5");
                startActivity(intent);
            }
        });

    }
}
