package me.hongui.learngerman.bean;

public enum Category {
    NUMBER(1,"数字学习(1)"),ORDER(2,"序数学习"), NUMBER_INPUT(3,"数字填空"),
    IMAGE_OPTION(4,"看图选数"), WATCH_INPUT(5,"看数写数"), VOICE_OPTION(6,"听数选择"),
    VOICE_INPUT(7,"听数键入"), VOICE_INPUT_ADVANCE(8,"听数键入-高级"),
    WATCH_INPUT_ADVANCE(9,"看数写数-高级"), VOICE_OPTION_ADVANCE(10,"听数选择-高级"),
    NUMBER_EXPRESS(11,"数字学习(2)"), DATE_EXPRESS(12,"表示日期"), TIME_EXPRESS(13,"表示时间"),
    PRICE_EXPRESS(14,"价格表示"),BOOKMARK(9527,"收藏列表"),NUMBER_DIFFERENT(9528,"数字表达");

    public int category;
    public String name;

    private Category(int category, String name){
        this.category=category;
        this.name=name;
    }

    public static String text(int value){
        Category[] values = Category.values();
        for (Category c : values) {
            if(c.category==value){
                return c.name;
            }
        }
        return "";
    }
}
