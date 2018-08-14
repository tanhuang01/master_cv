## master_cv
custom view repository, just for exercising.

### 3.进度条

&nbsp;&nbsp;&nbsp;&nbsp; 叶子和风扇使用的是图片; 其中叶子使用 Matrix 来控制 旋转方向, 竖直方向上的位移满足 sin() 函数.  半圆弧效果使用 Canvas 的 clip 实现. 具体实现参考 *_03_loading_view* 包代码

![](https://github.com/tanhuang01/master_cv/blob/master/pic/_03_loading.gif)

### 9.带弹性的圆

&nbsp;&nbsp;&nbsp;&nbsp; 使用四条三阶贝塞尔曲线绘制一个圆. 通过控制点的位置切换来实现拉伸和回弹效果. 具体实现参考 *_09_bezier_view* 包代码

![](https://github.com/tanhuang01/master_cv/blob/master/pic/_09_bezier.gif)

### 10.搜索

&nbsp;&nbsp;&nbsp;&nbsp; PathMeasure 来控制绘制的长度. 具体实现参考 *_10_path_measure* 包代码.

![](https://github.com/tanhuang01/master_cv/blob/master/pic/_10_path_measure.gif)

### 17.图片文字

&nbsp;&nbsp;&nbsp;&nbsp; 使用 Paint 的 breakText 功能来控制每一行的字符数; 通过 FontMetric 来测量每一行的高度. 并且对图片位置做处理. 具体实现参考 *_17_CustomTextWithPicView* 代码

![](https://github.com/tanhuang01/master_cv/blob/master/pic/_17_text_pic.png)

### 18.图片特效(1)

&nbsp;&nbsp;&nbsp;&nbsp; 这个图片特效感觉十分的给力, 所以就模仿了一下下. 具体实现 *_18_anim* 包下的代码.

![](https://github.com/tanhuang01/master_cv/blob/master/pic/_18_map_anim.gif)

### 19.meteial_edit_text

&nbsp;&nbsp;&nbsp;&nbsp; 仿写任务线大神的 MaterialEditText. 具体实现 *_19_MaterialEditText*.

![](https://github.com/tanhuang01/master_cv/blob/master/pic/_19_meterial_degsin.gif)

### 20. 图片缩放.

&nbsp;&nbsp;&nbsp;&nbsp; 简单的图片缩放功能. *ScalePicView*

![](https://github.com/tanhuang01/master_cv/blob/master/pic/_21_scale_pic.gif)