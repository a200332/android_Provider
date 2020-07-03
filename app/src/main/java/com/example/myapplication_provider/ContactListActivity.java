package com.example.myapplication_provider;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactListActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ContactAdapter adapter;
    private List<Map<String, String>> data = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        listView = getListView();
        adapter = new ContactAdapter();
        ContentResolver resolver = getContentResolver();
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String number = cursor.getString(1);
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", name);
            map.put("number", number);
            data.add(map);
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String number = data.get(i).get("number");
        Intent intent = getIntent();
        intent.putExtra("NUMBER", number);
        setResult(RESULT_OK, intent);
        finish();
    }

    class ContactAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(ContactListActivity.this, R.layout.item_contact, null);
            }
            Map<String, String> map = data.get(i);
            TextView nameTV = view.findViewById(R.id.tv_item_name);
            TextView numberTV = view.findViewById(R.id.tv_item_number);
            nameTV.setText(map.get("name"));
            numberTV.setText(map.get("number"));
            return view;
        }
    }
}
