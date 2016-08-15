# GoldDrop
一个酷炫的钱包掉落动画

##Demo
![image] (https://github.com/JangGwa/GoldDrop/blob/master/GoldDrop.gif)

##Use
- 声音文件保存在/res/raw中，可以自己替换
- 设置背景
  this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
- 设置同时出现在屏幕上的数量 建议64以内 过多会引起卡顿
  flakeView.addFlakes(38);
- 主题，布局，文字都可以很方便的修改调用
