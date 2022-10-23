package net.yiliufeng.windows_control.MyUtils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestUtil {

    private  static OkHttpClient client=new OkHttpClient();

    public static String baseRequest(String strurl) {
        Request request=new Request.Builder()
                .url(strurl)
                .get()
                .build();
        Response response=null;
        try {
            response=client.newCall(request).execute();
            if(response.isSuccessful()){
                return response.body().string();
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String baseRequest(HttpUrl strurl) {
        Request request=new Request.Builder()
                .url(strurl)
                .get()
                .build();
        Response response=null;
        try {
            response=client.newCall(request).execute();
            if(response.isSuccessful()){
                return response.body().string();
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)

                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    public static int doInsert(String question, String answer) throws UnsupportedEncodingException {
        /*
        成功 ：  1--》插入成功
                0--》插入失败
               -1 ---》网络错误
         */
        String response=baseRequest("https://www.yiliufeng.net:8000/xxqg/add/?question=" + URLEncoder.encode(question, "utf-8") + "&answer=" + URLEncoder.encode(answer, "utf-8"));
        if(response!=null)
            if(response.equals("true"))
                return 1;
            else
                return 0;
        else
            return -1;

    }

    public static int doQuery(String question, String answer) throws UnsupportedEncodingException {
        String response=baseRequest("https://www.yiliufeng.net:8000/xxqg/query/?question=" + URLEncoder.encode(question, "utf-8") + "&answer=" + URLEncoder.encode(answer, "utf-8"));
        if(response!=null)
            if(response.equals("true"))
                return 1;
            else
                return 0;
        else
            return -1;

    }

    public static int doDel(String question, String answer) throws UnsupportedEncodingException {
        String response= baseRequest("https://www.yiliufeng.net:8000/xxqg/del/?question=" + URLEncoder.encode(question, "utf-8") + "&answer=" + URLEncoder.encode(answer, "utf-8"));
        if(response!=null)
            if(response.equals("true"))
                return 1;
            else
                return 0;
        else
            return -1;
    }

    public static int doGetCount() {
        String response=baseRequest("https://www.yiliufeng.net:8000/xxqg/getcount/");
        if (response!=null){
            return Integer.parseInt(response);
        }else {
            return 0;
        }


//            URL url = new URL("https://www.yiliufeng.net:8000/xxqg/getcount/");
//            conn = (HttpsURLConnection) url.openConnection();
//
//            conn.setConnectTimeout(5000);
//            conn.setRequestMethod("GET");
//            if (conn.getResponseCode() == 200) {
//                InputStream in = conn.getInputStream();
//                byte[] data = StreamTool.read(in);
//                String html = new String(data, "UTF-8");
//                Log.i("tzdt", "接收内容：" + html);
//                return Integer.parseInt(html);
//            } else {
//                Log.i("tzdt", "error");
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
    }
}
