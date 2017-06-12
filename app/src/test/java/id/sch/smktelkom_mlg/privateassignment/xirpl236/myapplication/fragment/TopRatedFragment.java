package id.sch.smktelkom_mlg.privateassignment.xirpl236.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.privateassignment.xirpl202.themoviedatabase.R;
import id.sch.smktelkom_mlg.privateassignment.xirpl202.themoviedatabase.adapter.SourceAdapter;
import id.sch.smktelkom_mlg.privateassignment.xirpl202.themoviedatabase.model.Source;
import id.sch.smktelkom_mlg.privateassignment.xirpl202.themoviedatabase.model.SourcesResponse;
import id.sch.smktelkom_mlg.privateassignment.xirpl202.themoviedatabase.service.GsonGetRequest;
import id.sch.smktelkom_mlg.privateassignment.xirpl202.themoviedatabase.service.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopRatedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopRatedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRatedFragment extends Fragment {
    ArrayList<Source> mList = new ArrayList<>();
    SourceAdapter mAdapter;

    public TopRatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SourceAdapter(this.getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

        downloadDataSource();
    }

    private void downloadDataSource() {
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=d4bee1442fda04e0b421566f1a54e4ae";

        GsonGetRequest<SourcesResponse> myRequest = new GsonGetRequest<SourcesResponse>
                (url, SourcesResponse.class, null, new Response.Listener<SourcesResponse>() {

                    @Override
                    public void onResponse(SourcesResponse response) {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));
                        if (response.page.equals("1")) {
                            mList.addAll(response.results);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(myRequest);
    }

}

