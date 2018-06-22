# EasyNet
1.依赖：在模块中implementation 'com.github.chenchangqings:EasyNet:v1.0.6'
<br> 在项目的总配置中加入maven { url 'https://www.jitpack.io' }
<br>
<br>

2.初始化：在项目application中配置初始化：
<br>默认初始化：OkGoClient.getInstence().init(this);
<br>开启并设置全局请求Log：OkGoClient.getInstence().setLog("like").init(this);
<br>配置自己的OKhttp：OkGoClient.getInstence().init(this,MyOkHttpClient.build());
<br>
<br>


3.平常的请求方式:
<br>//请求Model（默认为全局Post请求）
<br>public  void requestModel(int tag, String path, Object params, Class<?> cls, ModelCallback callback){
<br>  }
<br>自定义post或者get请求(比如RequestMethod.GET)
<br>public  <T>void request(int tag,Enum method,String path, Object params,Class<?> cls, ModelCallback callback){
<br> }
<br>
<br>例：OkGoClient.getInstence().request(.........);
<br>
<br>

3.动态的请求方式：
<br>①配置自己的API:
<br>public interface TestApi {
<br>    @DoRequst(code=10001,url=UrlConfig.URL_VIDEOS)
<br>    DogBean getVideo(Object param, IRequestCallBack callBack);
<br>}
<br>解释：DogBean为请求返回的数据需要被解析成的对象，code是你自己请的请求码（方便区分请求），url是请求的网路路径，Object 是请求的参数，IRequestCallBack 是回调
<br>②设置统一的请求处理:
<br>public class BaseRequset implements IMethod {
<br>//根据类动态创建实例
<br>public <T> T create(final Class<T> service){
 <br>       return new EasyNetFactory().create(service,this);
<br>}
<br>//开始请求时会回调到这边(可以在这里统一处理自己请求)
<br>@Override
<br>public void doMethod(int code, String url, Object params, Class<?> cls,Object)
<br>OkGoClient.getInstence().request(.......);
<br>}
<br>}
