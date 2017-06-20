package com.cloudring.magicsound.iconduct.base;


public interface IConduct<T> {

    /***
     * 执行的入口
     * @param json 原始数据
     */
    void execute(String json);

    /**
     * 在此处对业务进行处理
     */
    void hand(T t);

    /***
     * 解析讯飞数据
     *
     * @param json 原始数据
     * @return 场景实体
     */
    T pareseIfly(String json);

    /***
     * 解析思必驰数据
     *
     * @param json 原始数据
     * @return 场景实体
     */
    T parseAISpeech(String json);

}
