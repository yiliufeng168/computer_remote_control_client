# 电脑遥控器 - Android 
## 说明
用于遥控安装了服务端的电脑(仅限Windows)
服务端地址<https://github.com/yiliufeng168/computer_remote_control_server>

## 功能说明
可以控制控制电脑关机、发送文本、控制鼠标、控制声音等，此外还支持自定义命令。

自定义命令可以对多条命令进行组合，形成一键自动化操作。

## 使用方式
1. 点击左上角加号，添加主机信息

![图片](https://github.com/yiliufeng168/computer_remote_control_client/blob/main/readme_img/add_computer.png)
![图片](https://github.com/yiliufeng168/computer_remote_control_client/blob/main/readme_img/computer_info.png)

2. 长按列表项可以对主机信息进行编辑和删除

![图片](https://github.com/yiliufeng168/computer_remote_control_client/blob/main/readme_img/edit_computer.png)

3. 点击列表某项可以进入遥控器页面

![图片](https://github.com/yiliufeng168/computer_remote_control_client/blob/main/readme_img/controller.png)
注意：当前页面`浏览器`和`待定`功能按钮还未实现。音乐默认打开网易云音乐。

4. 点击更多可以添加自定义按钮

![图片](https://github.com/yiliufeng168/computer_remote_control_client/blob/main/readme_img/controller.png)
长按按钮可以修改删除

## 自定义功能配置
目前支持的命令类型
### predefined 
预定义命令，包含以下命令
1. volume_up
    系统音量 +
2. volume_down
    系统音量 -
3. mute
    系统静音
4. close_window
    关闭窗口
5. backspace
    退格
6. shutdown
    关机
7. mouse_up
    鼠标向上移动10像素
8. mouse_down
    鼠标向下移动10像素
9. mouse_left
    鼠标向左移动10像素
10. mouse_right
    鼠标向右移动10像素        
11. mouse_left_click
    鼠标左键点击
12. open_cloudmusic
    打开网易云音乐
13. close_cloudmusic
    关闭网易云音乐
14. exit_app
    终止服务端

### keyboard
该命令类型可以向电脑发送键盘敲击事件，命令字段可填写具体的按键如`A`、`1`、`left`,也可以是组合键如`ctrl+A`

### mouse
该命令类型能够控制鼠标移动，命令字段格式为
```json 
{"dx": 0.5,"dy":1}
```
参数字段为Int类型，为移动距离(distance)，可以忽略，默认为20像素

电脑接收到该命令后，鼠标将向右移动`dx*distance`的距离，向下的移动`dx*distance`的距离。

### input_msg
该命令类型能向文本框发送文字，命令字段填写需要发送的内容。需注意该命令利用了剪切板进行发送文本。

### browser
该命令类型将调用谷歌浏览器驱动对浏览器进行控制。


### shell
该命令类型将执行cmd命令，命令字段填写具体的命令。

命令字段可选：
1. open_url
    打开指定url地址，参数字段为具体url地址，如`https://www.baidu.com`
2. click_by_xpath
    根据xpath查找元素并点击，参数字段为xpath
    
3. quit
    退出驱动
4. input_text
    文本输入，参数为json格式，如：
 ```json
{"xpath":"//*[@id=\"kw\"]","text":"你好"}
```
    需注意双引号需转义
5. next_tab
    跳转到下一个标签页
6. 新建标签页


# 联系
如有更多好的建议请发送邮件至 positivelamb@outlook.com 
     