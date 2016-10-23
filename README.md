# Android Basic - Examples
Example using Google Maps

##Steps##

Go to :https://console.developers.google.com

Get the API KEY

Add package name and fingerprint, then copy the KEY

Add <metadata> in AndroidManifiest.xml

       <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="@string/google_maps_key" />
            
            


####Get the location from web Service####

- Add the libraries in your Gradle
       ```
       android {
              useLibrary 'org.apache.http.legacy'
       }

       dependencies {
              compile 'org.apache.httpcomponents:httpcore:4.4.1'
              compile 'org.apache.httpcomponents:httpclient:4.5'
       }
       ```
       
- Create http request class
       ```
       public class GetMethodEx {
           public String getInternetData() throws Exception{
               BufferedReader in = null;
               String data = null;
               try
               {
                   HttpClient client = new DefaultHttpClient();
                   URI website = new URI("http://clinife.com.pe/get_coordenadas.php");
                   HttpGet request = new HttpGet();
                   request.setURI(website);
                   HttpResponse response = client.execute(request);
                   response.getStatusLine().getStatusCode();

                   in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                   StringBuffer sb = new StringBuffer("");
                   String l = "";
                   String nl = System.getProperty("line.separator");
                   while ((l = in.readLine()) !=null){
                       sb.append(l + nl);
                   }
                   in.close();
                   data = sb.toString();
                   return data;
               } finally{
                   if (in != null){
                       try{
                           in.close();
                           return data;
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                   }
               }
           }
       }
       ```
       
       
- Create the Buton and set the OnClickListener

       ```
          Button btn = (Button) rootView.findViewById(R.id.cargar_ubicacion);
               btn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       cargarUbicacion();
                   }
          });
       ```
        
- load location
       ```
       private void cargarUbicacion() {
              new LongOperation().execute("");
       }
       ```
    
- Make the request in the AsynTask
    ```
    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            GetMethodEx test = new GetMethodEx();
            String returned = null;
            try {
                returned = test.getInternetData();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return returned;
        }
        @Override
        protected void onPostExecute(String result) {
            JSONObject jo = null;
            try {
                jo = new JSONObject(result);
                procesarJson(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    ```
    

- Read the values from Json
    ```
    private void procesarJson(JSONObject jo) throws JSONException {
        JSONArray coordenada = jo.getJSONArray("coordenada");
        for (int i=0; i<coordenada.length(); i++){
            JSONObject jsonCoordenadas = coordenada.getJSONObject(i);
            double latitud = jsonCoordenadas.getDouble("latitud");
            double longitud = jsonCoordenadas.getDouble("longitud");
            mostrarUbicacion(latitud, longitud);
        }
    }
    ```
    
    
- Show the location
       ```
          private void mostrarUbicacion(double latitud, double longitud) {
               LatLng coordinate = new LatLng(latitud, longitud);
               CameraUpdate tuUbicacion = CameraUpdateFactory.newLatLngZoom(coordinate, ZOOM_MAP);
               mMap.animateCamera(tuUbicacion);

               mostrarMarcador(coordinate);
         }
       ```
         
- Show the marker
    ```
    private void mostrarMarcador(LatLng coordinate) {
        MarkerOptions mp = new MarkerOptions();
        mp.position(coordinate);
        mp.title("Ubicacon Encontrada");
        mMap.addMarker(mp);
    }
    ```
