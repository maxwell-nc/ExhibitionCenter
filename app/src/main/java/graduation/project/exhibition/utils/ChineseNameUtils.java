package graduation.project.exhibition.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 中文名工具类
 */
public class ChineseNameUtils {

    /**
     * 百家姓
     */
    private static final String[] familyName = {
            "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩",
            "杨", "朱", "秦", "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏",
            "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚",
            "范", "彭", "郎", "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳",
            "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷", "罗",
            "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余",
            "元", "卜", "顾", "孟", "平", "黄", "和", "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁",
            "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞",
            "熊", "纪", "舒", "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季", "麻",
            "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭", "梅", "盛", "林", "刁", "钟", "徐",
            "邱", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝",
            "管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应", "宗", "丁", "宣", "贲", "邓",
            "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉", "钮", "龚", "程", "嵇", "邢",
            "滑", "裴", "陆", "荣", "翁", "荀", "羊", "于", "惠", "甄", "曲", "家", "封", "芮", "羿",
            "储", "靳", "汲", "邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓", "牧",
            "隗", "山", "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊", "宫",
            "宁", "仇", "栾", "暴", "甘", "钭", "厉", "戎", "祖", "武", "符", "刘", "景", "詹", "束",
            "龙", "叶", "幸", "司", "韶", "郜", "黎", "蓟", "溥", "印", "宿", "白", "怀", "蒲", "邰",
            "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺", "屠", "蒙", "池", "乔", "阴", "郁", "胥",
            "能", "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申", "扶", "堵",
            "冉", "宰", "郦", "雍", "却", "璩", "桑", "桂", "濮", "牛", "寿", "通", "边", "扈", "燕",
            "冀", "浦", "尚", "农", "温", "别", "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹",
            "习", "宦", "艾", "鱼", "容", "向", "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居",
            "衡", "步", "都", "耿", "满", "弘", "匡", "国", "文", "寇", "广", "禄", "阙", "东", "欧",
            "殳", "沃", "利", "蔚", "越", "夔", "隆", "师", "巩", "厍", "聂", "晁", "勾", "敖", "融",
            "冷", "訾", "辛", "阚", "那", "简", "饶", "空", "曾", "毋", "沙", "乜", "养", "鞠", "须",
            "丰", "巢", "关", "蒯", "相", "查", "后", "荆", "红", "游", "郏", "竺", "权", "逯", "盖",
            "益", "桓", "公", "仉", "督", "岳", "帅", "缑", "亢", "况", "郈", "有", "琴", "归", "海",
            "晋", "楚", "闫", "法", "汝", "鄢", "涂", "钦", "商", "牟", "佘", "佴", "伯", "赏", "墨",
            "哈", "谯", "篁", "年", "爱", "阳", "佟", "言", "福", "南", "火", "铁", "迟", "漆", "官",
            "冼", "真", "展", "繁", "檀", "祭", "密", "敬", "揭", "舜", "楼", "疏", "冒", "浑", "挚",
            "胶", "随", "高", "皋", "原", "种", "练", "弥", "仓", "眭", "蹇", "覃", "阿", "门", "恽",
            "来", "綦", "召", "仪", "风", "介", "巨", "木", "京", "狐", "郇", "虎", "枚", "抗", "达",
            "杞", "苌", "折", "麦", "庆", "过", "竹", "端", "鲜", "皇", "亓", "老", "是", "秘", "畅",
            "邝", "还", "宾", "闾", "辜", "纵", "侴", "万俟", "司马", "上官", "欧阳", "夏侯", "诸葛",
            "闻人", "东方", "赫连", "皇甫", "羊舌", "尉迟", "公羊", "澹台", "公冶", "宗正", "濮阳",
            "淳于", "单于", "太叔", "申屠", "公孙", "仲孙", "轩辕", "令狐", "钟离", "宇文", "长孙",
            "慕容", "鲜于", "闾丘", "司徒", "司空", "兀官", "司寇", "南门", "呼延", "子车", "颛孙",
            "端木", "巫马", "公西", "漆雕", "车正", "壤驷", "公良", "拓跋", "夹谷", "宰父", "谷梁",
            "段干", "百里", "东郭", "微生", "梁丘", "左丘", "东门", "西门", "南宫", "第五", "公仪",
            "公乘", "太史", "仲长", "叔孙", "屈突", "尔朱", "东乡", "相里", "胡母", "司城", "张廖",
            "雍门", "毋丘", "贺兰", "綦毋", "屋庐", "独孤", "南郭", "北宫", "王孙"
    };

    /**
     * 常用人名
     */
    private static final String[] frequentlyName = {
            "张伟", "王伟", "王芳", "李伟", "李娜", "张敏", "李静", "王静", "刘伟", "张丽", "王丽", "张静", "李强",
            "王敏", "李敏", "王磊", "刘洋", "王艳", "王勇", "李军", "张勇", "李杰", "张杰", "张磊", "王强", "李娟",
            "王军", "张艳", "张涛", "王涛", "李艳", "王超", "李明", "李勇", "王娟", "刘杰", "刘敏", "李霞", "李丽",
            "张军", "王杰", "张强", "王秀兰", "王刚", "王平", "刘芳", "张燕", "刘艳", "刘军", "李平", "王辉",
            "王燕", "陈静", "刘勇", "李玲", "李桂英", "王丹", "李刚", "李丹", "李萍", "王鹏", "刘涛", "陈伟",
            "张华", "刘静", "李涛", "王桂英", "张秀兰", "李红", "李超", "刘丽", "张桂英", "王玉兰", "李燕", "张鹏"
            , "李秀兰", "张超", "王玲", "张玲", "李华", "王飞", "张玉兰", "王桂兰", "王英", "刘强", "陈秀英", "李英"
            , "李辉", "李梅", "陈勇", "王鑫", "李芳", "张桂兰", "李波", "杨勇", "王霞", "李桂兰", "王斌", "李鹏",
            "张平", "张莉", "张辉", "张宇", "刘娟", "李斌", "王浩", "陈杰", "王凯", "陈丽", "陈敏", "王秀珍",
            "李玉兰", "刘秀英", "王萍", "王萍", "张波", "刘桂英", "杨秀英", "张英", "杨丽", "张健", "李俊",
            "李莉", "王波", "张红", "刘丹", "李鑫", "王莉", "杨静", "刘超", "张娟", "杨帆", "刘燕", "刘英",
            "李雪", "李秀珍", "张鑫", "王健", "刘玉兰", "刘辉", "刘波", "张浩", "张明", "陈燕", "张霞", "陈艳",
            "杨杰", "王帅", "李慧", "王雪", "杨军", "张旭", "刘刚", "王华", "杨敏", "王宁", "李宁", "王俊",
            "刘桂兰", "刘斌", "张萍", "王婷", "陈涛", "王玉梅", "王娜", "张斌", "陈龙", "李林", "王玉珍", "张凤英",
            "王红", "李凤英", "杨洋", "李婷", "张俊", "王林", "陈英", "陈军", "刘霞", "陈浩", "张凯", "王晶",
            "陈芳", "张婷", "杨涛", "杨波", "陈红", "刘欢", "王玉英", "陈娟", "陈刚", "王慧", "张颖", "张林",
            "张娜", "张玉梅", "王凤英", "张玉英", "李红梅", "刘佳", "刘磊", "张倩", "刘鹏", "王旭", "张雪", "李阳",
            "张秀珍", "王梅", "王建华", "李玉梅", "王颖", "刘平", "杨梅", "李飞", "王亮", "李磊", "李建华", "王宇",
            "陈玲", "张建华", "刘鑫", "王倩", "张帅", "李健", "陈林", "李洋", "陈强", "赵静", "王成", "张玉珍",
            "陈超", "陈亮", "刘娜", "王琴", "张兰英", "张慧", "刘畅", "李倩", "杨艳", "张亮", "张建", "李云",
            "张琴", "王兰英", "李玉珍", "刘萍", "陈桂英", "刘颖", "杨超", "张梅", "陈平", "王建", "刘红", "赵伟",
            "张云", "张宁", "杨林", "张洁", "高峰", "王建国", "杨阳", "陈华", "杨华", "王建军", "杨柳", "刘阳",
            "王淑珍", "杨芳", "李春梅", "刘俊", "王海燕", "刘玲", "陈晨", "王欢", "李冬梅", "张龙", "陈波", "陈磊",
            "王云", "王峰", "王秀荣", "王瑞", "李琴", "李桂珍", "陈鹏", "王莹", "刘飞", "王秀云", "陈明", "王桂荣",
            "李浩", "王志强", "张丹", "李峰", "张红梅", "刘凤英", "李玉英", "王秀梅", "李佳", "王丽娟", "陈辉",
            "张婷婷", "张芳", "王婷婷", "王玉华", "张建国", "李兰英", "王桂珍", "李秀梅", "陈玉兰", "陈霞",
            "刘凯", "张玉华", "刘玉梅", "刘华", "李兵", "张雷", "王东", "李建军", "刘玉珍", "王琳", "李建国",
            "李颖", "杨伟", "李桂荣", "王龙", "刘婷", "陈秀兰", "张建军", "李秀荣", "刘明", "周敏", "张秀梅",
            "李雪梅", "黄伟", "张海燕", "王淑兰", "李志强", "杨磊", "李晶", "李婷婷", "张秀荣", "刘建华",
            "王丽丽", "赵敏", "陈云", "李海燕", "张桂荣", "张晶", "刘莉", "李凯", "张玉", "张峰", "刘秀兰",
            "张志强", "李龙", "李秀云", "李秀芳", "李帅", "李欣", "刘云", "张丽丽", "李洁", "张秀云", "王淑英",
            "王春梅", "王红梅", "陈斌", "李玉华", "李桂芳", "张莹", "陈飞", "王博", "刘浩", "黄秀英", "刘玉英",
            "李淑珍", "黄勇", "周伟", "王秀芳", "王丽华", "王丹丹", "李彬", "王桂香", "王坤", "刘慧", "李想",
            "张瑞", "张桂珍", "王淑华", "刘帅", "张飞", "张秀芳", "王洋", "陈洁", "张桂芳", "张丽娟", "王荣",
            "吴秀英", "杨明", "李桂香", "马丽", "刘倩", "杨秀兰", "杨玲", "王秀华", "杨平", "王彬", "李亮", "李荣",
            "李桂芝", "李琳", "李岩", "李建", "王兵", "王桂芳", "王明", "陈梅", "张春梅", "李杨", "王岩", "王冬梅",
            "刘峰", "李秀华", "李丹丹", "杨雪", "刘玉华", "马秀英", "张丽华", "张淑珍", "李小红", "张博", "王欣",
            "王桂芝", "赵丽", "张秀华", "张琳", "黄敏", "杨娟", "王金凤", "周杰", "王雷", "陈建华", "刘梅",
            "杨桂英", "李淑英", "陈玉英", "杨秀珍", "孙秀英", "赵军", "赵勇", "刘兵", "杨斌", "李文", "陈琳",
            "陈萍", "孙伟", "张利", "陈俊", "张楠", "刘桂珍", "刘宇", "刘建军", "张淑英", "李红霞", "赵秀英",
            "李博", "王利", "张荣", "张帆", "王建平", "张桂芝", "张瑜", "周勇", "张坤", "徐伟", "王桂花", "刘琴",
            "周静", "徐敏", "刘婷婷", "徐静", "杨红", "王璐", "张淑兰", "张文", "杨燕", "陈桂兰", "周丽", "李淑华",
            "陈鑫", "马超", "刘建国", "李桂花", "王凤兰", "李淑兰", "陈秀珍"
    };


    /**
     * 常用人名对应拼音
     */
    private static final String[] phoneticName = {
            "zhangwei", "wangwei", "wangfang", "liwei", "lina", "zhangmin", "lijing", "wangjing", "liuwei",
            "wangxiuying", "zhangli", "lixiuying", "wangli", "zhangjing", "zhangxiuying", "liqiang",
            "wangmin", "limin", "wanglei", "liuyang", "wangyan", "wangyong", "lijun", "zhangyong", "lijie",
            "zhangjie", "zhanglei", "wangqiang", "lijuan", "wangjun", "zhangyan", "zhangtao", "wangtao",
            "liyan", "wangchao", "liming", "liyong", "wangjuan", "liujie", "liumin", "lixia", "lili",
            "zhangjun", "wangjie", "zhangqiang", "wangxiulan", "wanggang", "wangping", "liufang",
            "zhangyan", "liuyan", "liujun", "liping", "wanghui", "wangyan", "chenjing", "liuyong",
            "liling", "liguiying", "wangdan", "ligang", "lidan", "liping", "wangpeng", "liutao", "chenwei",
            "zhanghua", "liujing", "litao", "wangguiying", "zhangxiulan", "lihong", "lichao", "liuli",
            "zhangguiying", "wangyulan", "liyan", "zhangpeng", "lixiulan", "zhangchao", "wangling",
            "zhangling", "lihua", "wangfei", "zhangyulan", "wangguilan", "wangying", "liuqiang",
            "chenxiuying", "liying", "lihui", "limei", "chenyong", "wangxin", "lifang", "zhangguilan",
            "libo", "yangyong", "wangxia", "liguilan", "wangbin", "lipeng", "zhangping", "zhangli",
            "zhanghui", "zhangyu", "liujuan", "libin", "wanghao", "chenjie", "wangkai", "chenli",
            "chenmin", "wangxiuzhen", "liyulan", "liuxiuying", "wangping", "wangping", "zhangbo",
            "liuguiying", "yangxiuying", "zhangying", "yangli", "zhangjian", "lijun4", "lili", "wangbo",
            "zhanghong", "liudan", "lixin", "wangli", "yangjing", "liuchao", "zhangjuan", "yangfan",
            "liuyan", "liuying", "lixue", "lixiuzhen", "zhangxin", "wangjian", "liuyulan", "liuhui",
            "liubo", "zhanghao", "zhangming", "chenyan", "zhangxia", "chenyan", "yangjie", "wangshuai",
            "lihui", "wangxue", "yangjun", "zhangxu", "liugang", "wanghua", "yangmin", "wangning", "lining",
            "wangjun", "liuguilan", "liubin", "zhangping", "wangting", "chentao", "wangyumei", "wangna",
            "zhangbin", "chenlong", "lilin", "wangyuzhen", "zhangfengying", "wanghong", "lifengying",
            "yangyang", "liting", "zhangjun", "wanglin", "chenying", "chenjun", "liuxia", "chenhao",
            "zhangkai", "wangjing", "chenfang", "zhangting", "yangtao", "yangbo", "chenhong", "liuhuan",
            "wangyuying", "chenjuan", "chengang", "wanghui", "zhangying", "zhanglin", "zhangna", "zhangyumei",
            "wangfengying", "zhangyuying", "lihongmei", "liujia", "liulei", "zhangqian", "liupeng", "wangxu",
            "zhangxue", "liyang", "zhangxiuzhen", "wangmei", "wangjianhua", "liyumei", "wangying", "liuping",
            "yangmei", "lifei", "wangliang", "lilei", "lijianhua", "wangyu", "chenling", "zhangjianhua",
            "liuxin", "wangqian", "zhangshuai", "lijian", "chenlin", "liyang", "chenqiang", "zhaojing",
            "wangcheng", "zhangyuzhen", "chenchao", "chenliang", "liuna", "wangqin", "zhanglanying", "zhanghui",
            "liuchang", "liqian", "yangyan", "zhangliang", "zhangjian", "liyun", "zhangqin", "wanglanying",
            "liyuzhen", "liuping", "chenguiying", "liuying", "yangchao", "zhangmei", "chenping", "wangjian",
            "liuhong", "zhaowei", "zhangyun", "zhangning", "yanglin", "zhangjie", "gaofeng", "wangjianguo",
            "yangyang", "chenhua", "yanghua", "wangjianjun", "yangliu", "liuyang", "wangshuzhen", "yangfang",
            "lichunmei", "liujun", "wanghaiyan", "liuling", "chenchen", "wanghuan", "lidongmei", "zhanglong",
            "chenbo", "chenlei", "wangyun", "wangfeng", "wangxiurong", "wangrui", "liqin", "liguizhen",
            "chenpeng", "wangying", "liufei", "wangxiuyun", "chenming", "wangguirong", "lihao", "wangzhiqiang",
            "zhangdan", "lifeng", "zhanghongmei", "liufengying", "liyuying", "wangxiumei", "lijia", "wanglijuan",
            "chenhui", "zhangtingting", "zhangfang", "wangtingting", "wangyuhua", "zhangjianguo", "lilanying",
            "wangguizhen", "lixiumei", "chenyulan", "chenxia", "liukai", "zhangyuhua", "liuyumei", "liuhua",
            "libing", "zhanglei", "wangdong", "lijianjun", "liuyuzhen", "wanglin", "lijianguo", "liying",
            "yangwei", "liguirong", "wanglong", "liuting", "chenxiulan", "zhangjianjun", "lixiurong", "liuming",
            "zhoumin", "zhangxiumei", "lixuemei", "huangwei", "zhanghaiyan", "wangshulan", "lizhiqiang",
            "yanglei", "lijing", "litingting", "zhangxiurong", "liujianhua", "wanglili", "zhaomin", "chenyun",
            "lihaiyan", "zhangguirong", "zhangjing", "liuli", "likai", "zhangyu", "zhangfeng", "liuxiulan",
            "zhangzhiqiang", "lilong", "lixiuyun", "lixiufang", "lishuai", "lixin", "liuyun", "zhanglili",
            "lijie", "zhangxiuyun", "wangshuying", "wangchunmei", "wanghongmei", "chenbin", "liyuhua",
            "liguifang", "zhangying", "chenfei", "wangbo", "liuhao", "huangxiuying", "liuyuying", "lishuzhen",
            "huangyong", "zhouwei", "wangxiufang", "wanglihua", "wangdandan", "libin", "wangguixiang",
            "wangkun", "liuhui", "lixiang", "zhangrui", "zhangguizhen", "wangshuhua", "liushuai", "zhangfei",
            "zhangxiufang", "wangyang", "chenjie", "zhangguifang", "zhanglijuan", "wangrong", "wuxiuying",
            "yangming", "liguixiang", "mali", "liuqian", "yangxiulan", "yangling", "wangxiuhua", "yangping",
            "wangbin", "liliang", "lirong", "liguizhi", "lilin", "liyan", "lijian", "wangbing", "wangguifang",
            "wangming", "chenmei", "zhangchunmei", "liyang", "wangyan", "wangdongmei", "liufeng", "lixiuhua",
            "lidandan", "yangxue", "liuyuhua", "maxiuying", "zhanglihua", "zhangshuzhen", "lixiaohong",
            "zhangbo", "wangxin", "wangguizhi", "zhaoli", "zhangxiuhua", "zhanglin", "huangmin", "yangjuan",
            "wangjinfeng", "zhoujie", "wanglei", "chenjianhua", "liumei", "yangguiying", "lishuying",
            "chenyuying", "yangxiuzhen", "sunxiuying", "zhaojun", "zhaoyong", "liubing", "yangbin", "liwen",
            "chenlin", "chenping", "sunwei", "zhangli", "chenjun", "zhangnan", "liuguizhen", "liuyu",
            "liujianjun", "zhangshuying", "lihongxia", "zhaoxiuying", "libo", "wangli", "zhangrong",
            "zhangfan", "wangjianping", "zhangguizhi", "zhangyu", "zhouyong", "zhangkun", "xuwei", "wangguihua",
            "liuqin", "zhoujing", "xumin", "liutingting", "xujing", "yanghong", "wanglu", "zhangshulan",
            "zhangwen", "yangyan", "chenguilan", "zhouli", "lishuhua", "chenxin", "machao", "liujianguo",
            "liguihua", "wangfenglan", "lishulan", "chenxiuzhen"
    };


    /**
     * 常用拼音
     */
    private static String[] phonetic = {"a", "ai", "an", "ang",
            "ao", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng",
            "bi", "bian", "biao", "bie", "bin", "bing", "bo", "bu", "ca",
            "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai", "chan",
            "chang", "chao", "che", "chen", "cheng", "chi", "chong", "chou",
            "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci",
            "cong", "cou", "cu", "cuan", "cui", "cun", "cuo", "da", "dai",
            "dan", "dang", "dao", "de", "deng", "di", "dian", "diao", "die",
            "ding", "diu", "dong", "dou", "du", "duan", "dui", "dun", "duo",
            "e", "en", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo",
            "fou", "fu", "ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen",
            "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui",
            "gun", "guo", "ha", "hai", "han", "hang", "hao", "he", "hei",
            "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang",
            "hui", "hun", "huo", "ji", "jia", "jian", "jiang", "jiao", "jie",
            "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka",
            "kai", "kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou",
            "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la",
            "lai", "lan", "lang", "lao", "le", "lei", "leng", "li", "lia",
            "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long",
            "lou", "lu", "lv", "luan", "lue", "lun", "luo", "ma", "mai", "man",
            "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao",
            "mie", "min", "ming", "miu", "mo", "mou", "mu", "na", "nai", "nan",
            "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang",
            "niao", "nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan",
            "nue", "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei",
            "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po",
            "pu", "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing",
            "qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao",
            "re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui",
            "run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen",
            "seng", "sha", "shai", "shan", "shang", "shao", "she", "shen",
            "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang",
            "shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui",
            "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng",
            "ti", "tian", "tiao", "tie", "ting", "tong", "tou", "tu", "tuan",
            "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen",
            "weng", "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie",
            "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya",
            "yan", "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong",
            "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang",
            "zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang",
            "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu",
            "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo", "zi",
            "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};

    /**
     * 获取随机中文
     * 原理是从汉字区位码找到汉字。在汉字区位码中分高位与底位，
     * 且其中简体又有繁体。位数越前生成的汉字繁体的机率越大。
     * 所以在本例中高位从171取，底位从161取，
     * 去掉大部分的繁体和生僻字。但仍然会有！！
     */
    public static String getRandomChinese(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int highPos, lowPos; // 定义高低位
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            highPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(highPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBk"); //转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

    /**
     * 产生中文名
     */
    public static String generate() {
        String name = phonetic[MethodUtils.randomInt(phonetic.length - 1)];
        name += phonetic[MethodUtils.randomInt(phonetic.length - 1)];
        name += phonetic[MethodUtils.randomInt(phonetic.length - 1)];
        name += "_" + Integer.toHexString(MethodUtils.randomInt(999));
        return name;
    }

}
