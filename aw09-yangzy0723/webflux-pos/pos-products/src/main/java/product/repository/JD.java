package product.repository;

import data.model.Product;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class JD implements ProductRepository{


    private List<Product> products = null;

    public static List<Product> parseJD(String keyword) throws IOException {
        //获取请求https://search.jd.com/Search?keyword=java
        String url = "https://search.jd.com/Search?keyword=" + keyword;
        //解析网页
        Connection.Response response = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .cookies(getStringStringMap()) // 将您的 cookies 添加到这里
                .execute();
        Document document = response.parse();
        //所有js的方法都能用
        Element element = document.getElementById("J_goodsList");
        //获取所有li标签
        Elements elements = element.getElementsByTag("li");
        //System.out.println(element.html());
        List<Product> list = new ArrayList<>();
        //获取元素的内容
        for (Element el : elements) {
            //关于图片特别多的网站，所有图片都是延迟加载的
            String id = el.attr("data-spu");
            String img = "https:".concat(el.getElementsByTag("img").eq(0).attr("data-lazy-img"));
            String price = el.getElementsByAttribute("data-price").text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            if (title.contains("，"))
                title = title.substring(0, title.indexOf("，"));

            Product product = new Product(id, title, Double.parseDouble(price), img);

            list.add(product);
        }
        return list;
    }

    private static Map<String, String> getStringStringMap() {
        String cookieString = "mba_muid=17168087246641732408746; __jdu=17168087246641732408746; pinId=WezoFPIMolNfrbRkMBJHgQ; pin=jd_VRfJyJqRzUxd; unick=HakunaMatata__-; _tp=E6ut2cttKPc5kmA0460GrQ%3D%3D; _pst=jd_VRfJyJqRzUxd; shshshfpa=6a2f2b07-87c8-0b22-33ea-8d213c11bc87-1716808759; shshshfpx=6a2f2b07-87c8-0b22-33ea-8d213c11bc87-1716808759; qrsc=3; __jdv=181111935%7Cdirect%7C-%7Cnone%7C-%7C1718283708326; mba_sid=17182837083298052898516708435.1; wlfstk_smdl=re9un1tjmtcohkugmcb3s9zhe5piodkf; 3AB9D23F7A4B3CSS=jdd03IAZ24JKWKLDFEHTRU2VXH3I6KSTZIBFEN37ISBYZNF42KXW2CEIL67Z5HNDVZVE75TPRYZR7OP4IUDMFX7BNPNMHKEAAAAMQCGYKWGIAAAAAD7WX5YFV43K5DIX; _gia_d=1; TrackID=1vipq9tkhsr_I_2JnzFcP1oDGzl3uQpESD1QJMll60Wb_vQQaQZNo4m6Hz_XVxUvXCkDOJiGDeNW_d7Sh8w1P8tVp2PI4k6MAZN46j6m1uLA; thor=5D46010185CDA7B2ED1AD192B514E5D94C36CACBCB16CB62F55B324B8DFCBDA079C47D9DCEEA4B431FB75425C5B8D5A6C13BA5B2ED6464AFE5AE14F3E6265CE0A5634EE062ABEC53DFFF74DC5E2DC33CBC82570843FF307E9D31FB9FD3E85CBB604B80E0ACDB18C69E4CDBE7CE73216C9B18ECAD87FF21CDB7FE74BA91FF646A7CF66A1019D480F0255188EC8D7C3662B8363780E0A8AB7C5AFC427B37B46578; flash=2_NCsnqTveurMNc0V_gYY9smP2oHQoXiKd0OvfVi_oXvyMlA0CsnNMgiBXBMHiXyvEEMVFBEAG2_yyexXOJhFwzSJXOP_p-JRJwlUso0EMWjDzWLytXV0H_kBtuSPUmuR_FKSecODdAOC183gOIQ9vnxKlK8m5Aerq_vvPb3lTmns*; ceshi3.com=000; jsavif=1; jsavif=1; xapieid=jdd03IAZ24JKWKLDFEHTRU2VXH3I6KSTZIBFEN37ISBYZNF42KXW2CEIL67Z5HNDVZVE75TPRYZR7OP4IUDMFX7BNPNMHKEAAAAMQCGYKWGIAAAAAD7WX5YFV43K5DIX; __jda=143920055.17168087246641732408746.1716808724.1717324216.1718283708.3; __jdb=143920055.3.17168087246641732408746|3.1718283708; __jdc=143920055; rkv=1.0; areaId=12; ipLoc-djd=12-904-0-0; shshshfpb=BApXcZaW5EvVA2YUXdY_lloEj5FaH1w1dBlTBgHZq9xJ1MoRXSYC2; 3AB9D23F7A4B3C9B=IAZ24JKWKLDFEHTRU2VXH3I6KSTZIBFEN37ISBYZNF42KXW2CEIL67Z5HNDVZVE75TPRYZR7OP4IUDMFX7BNPNMHKE";
        Map<String, String> cookies = new HashMap<>();
        String[] cookiesArray = cookieString.split("; ");
        for (String cookie : cookiesArray) {
            String[] parts = cookie.split("=");
            cookies.put(parts[0], parts[1]);
        }
        return cookies;
    }

    @Override
    public List<Product> allProducts() {
        try {
            if (products == null) {
//                String json = "[{"id":"14067396","name":"Java从入门到精通（第7版）（软件开发视频大讲堂） 团购50册以上优惠咨询：（010）89110538","price":37.6,"image":"https://img11.360buyimg.com/n7/jfs/t1/92880/40/37274/91485/64b0f246F28539098/16f93df907ef0d29.jpg.avif"},{"id":"10167771016","name":"【清华】Java从入门到精通(第7七版) java语言程序设计电脑编程基础计算机软件开发教程JAVA编程入门零基础自学书籍javascript Java入门经典","price":35.8,"image":"https://img14.360buyimg.com/n7/jfs/t1/176588/14/42623/190268/65defb2cFde50bf31/cef8c393c8e3a58b.jpg.avif"},{"id":"14419018","name":"【2024年全新译本】Effective Java中文版(原书第3版)(异步图书出品) 团购50册以上优惠咨询：（010）89110538","price":49.9,"image":"https://img13.360buyimg.com/n7/jfs/t1/235853/28/15150/73346/661497c6Fbad2a896/ededdb2b8a6b4c89.jpg.avif"},{"id":"10024790028954","name":"佳沃JAVA公路车蓝图R3油压碟刹16速轻量航空铝合金自行车维洛克VELOCE 亮光银 【S】建议身高162-169cm 【蓝图-R3油压碟刹】16速","price":2499,"image":"https://img13.360buyimg.com/n7/jfs/t1/216591/32/40790/108770/66545e37Fdee27f34/af77d074f0021521.jpg.avif"},{"id":"10025053462714","name":"佳沃JAVA公路自行车铝合金公路车全油碟刹18变速弯把单车御夫座AURIGA 黑色 S（身高:163-173 CM） 18变速","price":2999,"image":"https://img11.360buyimg.com/n7/jfs/t1/221998/1/33141/56183/655c595dFb5ebe1e0/6468dfa6e29c2182.jpg.avif"},{"id":"10025351482843","name":"佳沃JAVA公路自行车铝合金全油压碟刹变速桶轴男女弯把赛车佳沃VELOCE 亮光银 550：180-190 CM","price":2299,"image":"https://img11.360buyimg.com/n7/jfs/t1/135151/29/37124/55491/65d5a4a2Fa0a25fdd/063474a396f80b92.jpg.avif"},{"id":"13925750","name":"Head First Java 实战（第三版） 团购50册以上优惠咨询：（010）89110538","price":51.2,"image":"https://img10.360buyimg.com/n7/jfs/t1/171901/15/36339/81997/64337d25F2aad31db/1f8ea88d6ddcdf66.jpg.avif"},{"id":"13880892","name":"Java核心技术 第12版 套装共2册 团购50册以上优惠咨询：（010）89110538","price":149,"image":"https://img12.360buyimg.com/n7/jfs/t1/87954/34/36453/108712/6444f6aeFc8555531/31e6ec5958221f7b.jpg.avif"},{"id":"10025053462714","name":"佳沃JAVA公路自行车铝合金公路车全油碟刹18变速弯把单车御夫座AURIGA 黑色 M（身高:170-180 CM） 18变速","price":2999,"image":"https://img12.360buyimg.com/n7/jfs/t1/221998/1/33141/56183/655c595dFb5ebe1e0/6468dfa6e29c2182.jpg.avif"},{"id":"10024790028954","name":"佳沃JAVA公路车蓝图R3油压碟刹16速轻量航空铝合金自行车维洛克VELOCE 消光黑灰 【S】建议身高162-169cm 【蓝图-R3油压碟刹】16速","price":2499,"image":"https://img11.360buyimg.com/n7/jfs/t1/227022/15/18331/88165/66545e37F585d3012/419636dae9000a60.jpg.avif"},{"id":"10025053462714","name":"佳沃JAVA公路自行车铝合金公路车全油碟刹18变速弯把单车御夫座AURIGA 黑色 XS（身高:150-168CM） 18变速","price":2999,"image":"https://img10.360buyimg.com/n7/jfs/t1/221998/1/33141/56183/655c595dFb5ebe1e0/6468dfa6e29c2182.jpg.avif"},{"id":"10023916882555","name":"佳沃JAVA鱼雷6公路自行车双油压碟刹桶轴24速弯把破风单车SILURO6-TOP 【桶轴版】亮光白 【430 XXS】建议身高：153-158cm 【2024款-蓝图】-24速","price":4499,"image":"https://img12.360buyimg.com/n7/jfs/t1/193124/26/45008/98603/66546663F936eb8f8/f6723c15ba338482.jpg.avif"},{"id":"13752015","name":"Java官方编程手册（第12版·Java 17）套装上下册 团购50册以上优惠咨询：（010）89110538","price":164,"image":"https://img10.360buyimg.com/n7/jfs/t1/114155/12/37811/87318/6489673cF1512b9b6/73c9af8a0e7a2fc3.jpg.avif"},{"id":"10023916882555","name":"佳沃JAVA鱼雷6公路自行车双油压碟刹桶轴24速弯把破风单车SILURO6-TOP 【桶轴版】亮光白紫 【430 XXS】建议身高：153-158cm 【2024款-蓝图】-24速","price":4499,"image":"https://img11.360buyimg.com/n7/jfs/t1/223300/40/41434/97880/6654667bFd8c209aa/95f0b060f151e42b.jpg.avif"},{"id":"10025184941990","name":"佳沃JAVA SILURO6-TOP桶轴公路车鱼雷6油压碟刹24变速竞赛自行车 亮光黑 XS码 禧玛诺105套件","price":5019,"image":"https://img10.360buyimg.com/n7/jfs/t1/234728/35/17896/66820/664da7acF6db1f65f/05be8c6c17f8ca94.jpg.avif"},{"id":"10025100121123","name":"佳沃JAVA AURIGA御夫座新版18速油刹 新款升级公路车","price":2999,"image":"https://img11.360buyimg.com/n7/jfs/t1/132713/32/43624/60095/660e5d62Fcbd77dd3/44d8286794015b5f.jpg.avif"},{"id":"10023646205706","name":"拍拍 二手Java编程思想（第4版）95新 套装书下单前请咨询在线客服","price":20.96,"image":"https://img11.360buyimg.com/n7/jfs/t1/156973/24/34303/20373/63d64777Fa8915c41/36a26b8310dd262a.jpg.avif"},{"id":"10025053462714","name":"佳沃JAVA公路自行车铝合金公路车全油碟刹18变速弯把单车御夫座AURIGA 钛色 XS（身高:150-168CM） 18变速","price":2999,"image":"https://img11.360buyimg.com/n7/jfs/t1/238395/4/12034/58692/65a7a2d2F27641ca3/0266133061e6a093.jpg.avif"},{"id":"10025247739757","name":"佳沃JAVA公路自行车铝合金18速碟刹弯把男女成人弯把单赛御夫座AURIGA 亮光黑 【480 S】建议身高：164-171(cm) 18速","price":3199,"image":"https://img14.360buyimg.com/n7/jfs/t1/234366/26/18283/95780/66545a72Fb6eca069/402b50c998b8f1a6.jpg.avif"},{"id":"10025019651780","name":"尚奈网游周边我的世界Java+基岩版Minecraft微激活软码大师收藏版PC版 终·极版 简体中文","price":65,"image":"https://img14.360buyimg.com/n7/jfs/t1/228811/32/2907/150560/6551ca19F6a432880/1bfda3e84d647065.jpg.avif"},{"id":"10021335437051","name":"佳沃（JAVA） 公路自行车鱼雷3代碟刹22变速弯把男女碳纤维前叉男女成人赛车 3代黑色18速青春 18速 53#建议176-186cm 700C","price":2999,"image":"https://img11.360buyimg.com/n7/jfs/t1/184599/40/29087/106340/64bce61cF877a781c/b8a43596cf2c052b.jpg.avif"},{"id":"10025100535966","name":"佳沃JAVA/佳沃维洛克2024VELOCE16速油压碟刹桶轴耐力型公路车 消光灰黑 L【建议176-183】700C 16速","price":2299,"image":"https://img10.360buyimg.com/n7/jfs/t1/231336/31/5880/117130/656c9fdbF32aa6880/67bb241861533702.jpg.avif"},{"id":"10025100535966","name":"佳沃JAVA/佳沃维洛克2024VELOCE16速油压碟刹桶轴耐力型公路车 亮光银 M【建议169-176】700C 16速","price":2299,"image":"https://img13.360buyimg.com/n7/jfs/t1/229020/17/5963/132936/656ca03cFa884934c/b849f2155a2360d9.jpg.avif"},{"id":"10025351482843","name":"佳沃JAVA公路自行车铝合金全油压碟刹变速桶轴男女弯把赛车佳沃VELOCE 消光黑灰 490：165-175 CM","price":2299,"image":"https://img14.360buyimg.com/n7/jfs/t1/101352/26/47075/62183/65daf5f6F06acccc0/3950e13ecaf1e1df.jpg.avif"},{"id":"10024790028954","name":"佳沃JAVA公路车蓝图R3油压碟刹16速轻量航空铝合金自行车维洛克VELOCE 消光黑灰 【XS】建议身高156-162cm 【蓝图-R3油压碟刹】16速","price":2499,"image":"https://img12.360buyimg.com/n7/jfs/t1/227022/15/18331/88165/66545e37F585d3012/419636dae9000a60.jpg.avif"},{"id":"10025351482843","name":"佳沃JAVA公路自行车铝合金全油压碟刹变速桶轴男女弯把赛车佳沃VELOCE 消光黑灰 510：172-182 CM","price":2299,"image":"https://img10.360buyimg.com/n7/jfs/t1/101352/26/47075/62183/65daf5f6F06acccc0/3950e13ecaf1e1df.jpg.avif"},{"id":"10025424256179","name":"佳沃JAVA/佳沃鱼雷6-2024气动破风公路车24速油压碟刹SILURO6-TOP 香槟钛 L【建议净高179-183cm】","price":3999,"image":"https://img14.360buyimg.com/n7/jfs/t1/234688/6/14459/103044/65ec79bcF0c7aad78/6d499c90243cd4ae.jpg.avif"},{"id":"10025247739757","name":"佳沃JAVA公路自行车铝合金18速碟刹弯把男女成人弯把单赛御夫座AURIGA 亮光黑 【450 XS】建议身高：157-164(cm 18速","price":3199,"image":"https://img13.360buyimg.com/n7/jfs/t1/234366/26/18283/95780/66545a72Fb6eca069/402b50c998b8f1a6.jpg.avif"},{"id":"10024537535426","name":"佳沃JAVA/佳沃VELOCE维洛克公路车16变速轻量航空铝合金双碟刹自行车 亮光银 XS（身高：\u003C162cm) 16变速","price":2199,"image":"https://img13.360buyimg.com/n7/jfs/t1/204103/6/34267/64053/65154c6aFa6dc89ea/aae5635c575c9f46.jpg.avif"},{"id":"10025219565749","name":"JAVA佳沃维洛克3公路车2024铝合金16变速轻量弯把油压碟刹桶轴自行车 亮光银（24款","price":2299,"image":"https://img11.360buyimg.com/n7/jfs/t1/100457/28/46675/53278/65b865afFf0a0c488/23d80d5dfd4d6cda.jpg.avif"}]"
//                Type productListType = new TypeToken<List<Product>>() {
//                }.getType();
//                products = new Gson().fromJson(json, productListType);
                products = parseJD("Java");
            }
        }
        catch (IOException e) {
            products = new ArrayList<>();
        }
        return products;
        }

    @Override
    public Product findProduct(String productId) {
        for (Product p : allProducts()) {
            if (p.getId().equals(productId))
                return p;
        }
        return null;
    }
}