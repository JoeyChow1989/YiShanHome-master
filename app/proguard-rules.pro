# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# copyright zhonghanwen
#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
# 指定代码的压缩级别 0 - 7(指定代码进行迭代优化的次数，在Android里面默认是5，这条指令也只有在可以优化时起作用。)
-optimizationpasses 5
# 混淆时不会产生形形色色的类名(混淆时不使用大小写混合类名)
-dontusemixedcaseclassnames
# 指定不去忽略非公共的库类(不跳过library中的非public的类)
-dontskipnonpubliclibraryclasses
# 指定不去忽略包可见的库类的成员
-dontskipnonpubliclibraryclassmembers
#不进行优化，建议使用此选项，
-dontoptimize
 # 不进行预校验,Android不需要,可加快混淆速度。
-dontpreverify
# 屏蔽警告
-ignorewarnings
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 保护代码中的Annotation不被混淆
-keepattributes *Annotation*
# 避免混淆泛型, 这在JSON实体映射时非常重要
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
 #优化时允许访问并修改有修饰符的类和类的成员，这可以提高优化步骤的结果。
# 比如，当内联一个公共的getter方法时，这也可能需要外地公共访问。
# 虽然java二进制规范不需要这个，要不然有的虚拟机处理这些代码会有问题。当有优化和使用-repackageclasses时才适用。
#指示语：不能用这个指令处理库中的代码，因为有的类和类成员没有设计成public ,而在api中可能变成public
-allowaccessmodification
#当有优化和使用-repackageclasses时才适用。
-repackageclasses ''
 # 混淆时记录日志(打印混淆的详细信息)
 # 这句话能够使我们的项目混淆后产生映射文件
 # 包含有类名->混淆后类名的映射关系
-verbose
-renamesourcefileattribute SourceFile

#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
#继承activity,application,service,broadcastReceiver,contentprovider....不进行混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}

-keepclassmembers class * {
    void *(*Event);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#// natvie 方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#---------------------------------1.实体类---------------------------------
-keep class com.sshy.yjy.strore.mate.detail.TabEntity
-keep class com.sshy.yjy.strore.mate.main.cart.bean.** {*;}
-keep class com.sshy.yjy.strore.mate.main.index.bean.** {*;}
-keep class com.sshy.yjy.strore.mate.personal.list.GridBean
-keep class com.sshy.yjy.strore.mate.personal.list.ListBean
-keep class com.sshy.yjy.strore.mate.refresh.PagingBean
-keep class com.zzy.mate.picture.entity.** {*;}

#---------------------------------第三方包-------------------------------

#支付宝支付
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep public class * extends android.os.IInterface
#微信支付
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}
-keep class com.tencent.wxop.** { *; }
-dontwarn com.tencent.mm.**
-keep class com.tencent.mm.**{*;}
-keepclasseswithmembernames class ** {
}
-keepattributes Signature

-keep class sun.misc.Unsafe { *; }

#greendao3.2.0,此是针对3.2.0，如果是之前的，可能需要更换下包名
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**

-keep class com.ut.** {*;}
-dontwarn com.ut.**

-keep class com.ta.** {*;}
-dontwarn com.ta.**

-keep class anet.**{*;}
-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.**{*;}
-dontwarn anet.**
-dontwarn org.android.spdy.**
-dontwarn org.android.agoo.**

-keepclasseswithmembernames class com.xiaomi.**{*;}
-keep public class * extends com.xiaomi.mipush.sdk.PushMessageReceiver

-dontwarn com.xiaomi.push.service.b

-keep class org.apache.http.**
-keep interface org.apache.http.**
-dontwarn org.apache.**

#okhttp3.x
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
#sharesdk
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-keep class com.bytedance.**{*;}

-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*

## nineoldandroids-2.4.0.jar
-keep public class com.nineoldandroids.** {*;}

####################zxing#####################
#-keep class com.google.zxing.** {*;}
#-dontwarn com.google.zxing.**
##百度定位
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

## okhttp
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.{*;}
#retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**

#recyclerview-animators
-keep class jp.wasabeef.** {*;}
-dontwarn jp.wasabeef.*

#蒲公英内测平台
-libraryjars libs/pgyer_sdk_3.0.4.jar
-dontwarn com.pgyersdk.**
-keep class com.pgyersdk.** { *; }
-keep class com.pgyersdk.**$* { *; }

#multistateview
#-keep class com.kennyc.view.** { *; }
#-dontwarn com.kennyc.view.*
#
## universal-image-loader 混淆
#-dontwarn com.nostra13.universalimageloader.**
#-keep class com.nostra13.universalimageloader.** { *; }

#ormlite
#-keep class com.j256.**
#-keepclassmembers class com.j256.** { *; }
#-keep enum com.j256.**
#-keepclassmembers enum com.j256.** { *; }
#-keep interface com.j256.**
#-keepclassmembers interface com.j256.** { *; }
#umeng
# ========= 友盟 =================
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**


-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**

-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**

-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

#-keep class im.yixin.sdk.api.YXMessage {*;}
#-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep public class [com.sshy.yjy.strore].R$*{
    public static final int *;
}
-keep public class [strore.yjy.sshy.com.mate].R$*{
    public static final int *;
}

-keep public class [com.zzy.mate.picture].R$*{
    public static final int *;
}

-keep public class [com.zzy.mate.ucrop].R$*{
    public static final int *;
}

-keep public class [com.sshy.yjy.ui].R$*{
    public static final int *;
}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#友盟自动更新
-keep public class com.umeng.fb.ui.ThreadView {
}
-keep public class * extends com.umeng.**
# 以下包不进行过滤
-keep class com.umeng.** { *; }


#-ButterKnife 7.0
 -keep class butterknife.** { *; }
 -dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }
 -keepclasseswithmembernames class * {
  @butterknife.* <fields>;
 }
 -keepclasseswithmembernames class * {
 @butterknife.* <methods>;
 }


#AndFix
-keep class * extends java.lang.annotation.Annotation
-keepclasseswithmembernames class * {
    native <methods>;
}

#eventbus 3.0
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


#EventBus
-keepclassmembers class ** {
    public void onEvent*(**);
}
-keepclassmembers class ** {
public void xxxxxx(**);
}


################gson##################
#-keep class com.google.gson.** {*;}
#-keep class com.google.**{*;}
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
#-keep class com.google.gson.examples.android.model.** { *; }

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep public class * implements java.io.Serializable {*;}


# support-v4
#https://stackoverflow.com/questions/18978706/obfuscate-android-support-v7-widget-gridlayout-issue
-dontwarn android.support.v4.**
-dontwarn android.support.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep class android.support.** { *; }

# support-v7
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }

# support design
#@link http://stackoverflow.com/a/31028536
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
#-------------------------------------------------------------------------

# picasso
#-keep class com.squareup.picasso.** {*; }
#-dontwarn com.squareup.picasso.**

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# #  ######## greenDao混淆  ##########
# # -------------------------------------------
-keep class de.greenrobot.dao.** {*;}
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static Java.lang.String TABLENAME;
}
-keep class **$Properties
# #  ############### volley混淆  ###############
## # -------------------------------------------
#-keep class com.android.volley.** {*;}
#-keep class com.android.volley.toolbox.** {*;}
#-keep class com.android.volley.Response$* { *; }
#-keep class com.android.volley.Request$* { *; }
#-keep class com.android.volley.RequestQueue$* { *; }
#-keep class com.android.volley.toolbox.HurlStack$* { *; }
#-keep class com.android.volley.toolbox.ImageLoader$* { *; }

#jpush极光推送
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

#activeandroid
-keep class com.activeandroid.** { *; }
-dontwarn com.ikoding.app.biz.dataobject.**
-keep public class com.ikoding.app.biz.dataobject.** { *;}
-keepattributes *Annotation*

#log4j
-dontwarn org.apache.log4j.**
-keep class  org.apache.log4j.** { *;}
#下面几行 是环信即时通信的代码混淆
#-keep class com.easemob.** {*;}
#-keep class org.jivesoftware.** {*;}
#-dontwarn  com.easemob.**

#融云
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
# public *;
#}

-keepattributes Exceptions,InnerClasses

-keep class io.rong.** {*;}

-keep class * implements io.rong.imlib.model.MessageContent{*;}

-keepattributes Signature

-keepattributes *Annotation*

#-keep class sun.misc.Unsafe { *; }
#
#-keep class com.google.gson.examples.android.model.** { *; }
#
#-keepclassmembers class * extends com.sea_monster.dao.AbstractDao {
# public static java.lang.String TABLENAME;
#}
-keep class **$Properties
-dontwarn org.eclipse.jdt.annotation.**

#-keep class com.ultrapower.** {*;}
#高徳地图
#-dontwarn com.amap.api.**
#-dontwarn com.a.a.**
#-dontwarn com.autonavi.**
#-keep class com.amap.api.**  {*;}
#-keep class com.autonavi.**  {*;}
#-keep class com.a.a.**  {*;}
#---------------------------------反射相关的类和方法-----------------------
#在这下面写反射相关的类和方法，没有就不用写！




#---------------------------------与js互相调用的类------------------------
#在这下面写与js互相调用的类，没有就去掉这句话！

#---------------------------------自定义View的类------------------------
#在这下面写自定义View的类的类，没有就去掉这句话！
-keep class com.sshy.yjy.ui.dropmenu.DropDownMenu
-keep class com.sshy.yjy.ui.flowlayout.FlowLayout
-keep class com.sshy.yjy.ui.flowlayout.TagFlowLayout
-keep class com.sshy.yjy.ui.flowlayout.TagView
-keep class com.sshy.yjy.ui.goodsdetail.utils.GoodsDetailLayout
-keep class com.sshy.yjy.ui.loading.ViewLoading
-keep class com.sshy.yjy.ui.popwindow.CustomPopWindow


#SuperID
#由*郭宇翔*贡献混淆代码
#作者Github地址：https://github.com/yourtion
#-keep class **.R$* {*;}
#-keep class com.isnc.facesdk.aty.**{*;}
#-keep class com.isnc.facesdk.**{*;}
#-keep class com.isnc.facesdk.common.**{*;}
#-keep class com.isnc.facesdk.net.**{*;}
#-keep class com.isnc.facesdk.view.**{*;}
#-keep class com.isnc.facesdk.viewmodel.**{*;}
#-keep class com.matrixcv.androidapi.face.**{*;}

#retrofit2.x
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#Rxjava RxAndroid
-dontwarn rx.*
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}


#litepal
#-dontwarn org.litepal.
#-keep class org.litepal.* {*;}
#-keep enum org.litepal.*
#-keep interface org.litepal. {*; }
#-keep public class extends org.litepal.{*; }
#-keepattributes Annotation
#-keepclassmembers class extends org.litepal.crud.DataSupport.{*; }

#fastJson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

-dontwarn com.bigkoo.convenientbanner.**
-keep class com.bigkoo.convenientbanner.** {*;}

-dontwarn com.blankj.utilcode.**
-keep class com.blankj.utilcode.** {*;}

-dontwarn com.choices.divider.**
-keep class com.choices.divider.** {*;}

-dontwarn com.daimajia.androidanimations.library.**
-keep class com.daimajia.androidanimations.library.** {*;}

-dontwarn com.daimajia.easing.**
-keep class com.daimajia.easing.** {*;}

-dontwarn com.flyco.tablayout.**
-keep class com.flyco.tablayout.** {*;}

-dontwarn com.bolex.autoEx.**
-keep class com.bolex.autoEx.** {*;}

-dontwarn autovalue.shaded.com.**
-keep class autovalue.shaded.com.** {*;}

-dontwarn com.google.auto.value.**
-keep class com.google.auto.value.** {*;}

-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** {*;}

-dontwarn com.squareup.**
-keep class com.squareup.** {*;}

-dontwarn com.tbruyelle.rxpermissions2.**
-keep class com.tbruyelle.rxpermissions2.** {*;}

-dontwarn com.ToxicBakery.viewpager.transforms.**
-keep class com.ToxicBakery.viewpager.transforms.** {*;}

-dontwarn com.wang.avi.**
-keep class com.wang.avi.** {*;}

-dontwarn de.hdodenhof.circleimageview.**
-keep class de.hdodenhof.circleimageview.** {*;}

-dontwarn in.srain.cube.views.ptr.**
-keep class in.srain.cube.views.ptr.** {*;}

-dontwarn jp.co.cyberagent.android.gpuimage.**
-keep class jp.co.cyberagent.android.gpuimage.** {*;}

-dontwarn jp.wasabeef.glide.transformations.**
-keep class jp.wasabeef.glide.transformations.** {*;}

-dontwarn me.dm7.barcodescanner.core.**
-keep class me.dm7.barcodescanner.core.** {*;}

-dontwarn me.dm7.barcodescanner.zbar.**
-keep class me.dm7.barcodescanner.zbar.** {*;}

-dontwarn me.jessyan.autosize.**
-keep class me.jessyan.autosize.** {*;}

-dontwarn org.greenrobot.greendao.**
-keep class org.greenrobot.greendao.** {*;}

-dontwarn org.greenrobot.greendao.generator.**
-keep class org.greenrobot.greendao.generator.** {*;}

-dontwarn org.hamcrest.**
-keep class org.hamcrest.** {*;}

-dontwarn gnu.trove.**
-keep class gnu.trove.** {*;}

-dontwarn org.reactivestreams.**
-keep class org.reactivestreams.** {*;}

-dontwarn android.arch.**
-keep class android.arc.** {*;}

-dontwarn com.chad.library.**
-keep class com.chad.library.** {*;}

-dontwarn com.github.florent37.viewanimator.**
-keep class com.github.florent37.viewanimator.** {*;}

-dontwarn qiu.niorgai.**
-keep class qiu.niorgai.** {*;}

-dontwarn com.suke.widget.**
-keep class com.suke.widget.** {*;}

-dontwarn com.orhanobut.logger.**
-keep class com.orhanobut.logger.** {*;}

