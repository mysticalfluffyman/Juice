package major.com.juice_android.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import major.com.juice_android.MainActivity;
import major.com.juice_android.R;
import major.com.juice_android.model.Downloaded;
import major.com.juice_android.viewadapter.DownloadListAdapter;

public class DownloadsFragment extends Fragment {
    ArrayList<Downloaded> flist= new ArrayList<Downloaded>();
    ListView downloadlist;
    SharedPreferences sharedPreferences;
    String currentUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("Downloads");



        return inflater.inflate(R.layout.fragment_downloads, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downloadlist=view.findViewById(R.id.downloadlist);
        sharedPreferences=this.getContext().getSharedPreferences("loggedininfo",0);
        currentUsername=sharedPreferences.getString("username","");

        String path = Environment.getExternalStorageDirectory().toString()+"/JUICE";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles(
                new FilenameFilter() {
                    @Override
                    public boolean accept(File file, String s) {
                        return s.endsWith(currentUsername+".juice");
                    }
                }
        );
        flist.clear();
        Log.d("Files", "Size: "+ files.length);
        int i=0;
        for(File file :files){
            //Downloaded downloaded;
            Log.d("filecount", " :"+i);
            String a=String.valueOf(i);
            flist.add(new Downloaded(file.getName(),file.getPath(),a));
            i++;
        }




        DownloadListAdapter arrayAdapter =new DownloadListAdapter(this.getContext(),flist);
        downloadlist.setAdapter(arrayAdapter);



    }


}