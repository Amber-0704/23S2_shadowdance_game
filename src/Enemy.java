public class Enemy {
    private final static int X_MAX_RANGE = 900;
    private final static int X_MIN_RANGE = 100;

    private int speed = 1; // Enemy移动的速度
    // 由于Enemy只会左右移动，所以y轴不动。只有x轴动
    private int xAxis;
    private int yAxis;

    //每600帧会刷新一个,并且每次的位子移动都是只在x轴上面移动
    //要随机生成Enemy，存在一个Random的class去随机生成
    //需要写consturction
    // 一个update的function
    public void update(){
        draw();
        // x + speed
        if(xAxis >= X_MAX_RANGE || xAxis <= X_MIN_RANGE){
            directionChange();
        }
    }
    private void directionChange(){  //当x的超出范围时，改变速度，相当于就是改变方向
        if(speed == 1){
            speed = -1;
        } else {
            speed = 1;
        }
    }
    // 同时由于在lane里面已经check过Collision了，所以在这里不用计较他

    public void draw(){
        //根据x，y坐标画出来
    }

    public int getXAxis() {
        return xAxis;
    }

    public int getYAxis() {
        return yAxis;
    }
}
