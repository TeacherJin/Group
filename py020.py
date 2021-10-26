# -*- coding: utf-8 -*-

###########################################################################
# Python code generated with wxFormBuilder (version Oct 26 2018)
# http://www.wxformbuilder.org/
##
# PLEASE DO *NOT* EDIT THIS FILE!
###########################################################################

import wx
import wx.xrc
import os
import random
import chardet

###########################################################################
# Class MyFrame1
###########################################################################


class MyFrame1 (wx.Frame):

    filePath = "D:\\"  # 学生名单文件所在的路径，默认为打开对话框的起始位置
    randomFlag = True  # 是否要进行随机分组，True表示随机，False表示手工设置参数
    npg = 0              # 每组学生的人数，默认为每组0人
    students = list()     # 存储学生信息的列表
    a = list()
    b = list()
    c = list()            # 存储三类学生的列表
    result = list()      # 存储结果的列表

    def __init__(self, parent=None):
        wx.Frame.__init__(self, parent, id=wx.ID_ANY, title=wx.EmptyString, pos=wx.DefaultPosition,
                          size=wx.Size(586, 360), style=wx.DEFAULT_FRAME_STYLE | wx.TAB_TRAVERSAL)

        self.SetSizeHints(wx.DefaultSize, wx.DefaultSize)

        bSizer1 = wx.BoxSizer(wx.VERTICAL)

        bSizer2 = wx.BoxSizer(wx.HORIZONTAL)

        self.lab_file = wx.StaticText(
            self, wx.ID_ANY, u"请选择存储学生信息的文件：", wx.DefaultPosition, wx.DefaultSize, 0)
        self.lab_file.Wrap(-1)

        bSizer2.Add(self.lab_file, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        self.fp_fileChioce = wx.FilePickerCtrl(
            self, wx.ID_ANY, wx.EmptyString, u"选择文件", u"*.*", wx.DefaultPosition, wx.Size(300, -1), wx.FLP_DEFAULT_STYLE)
        self.fp_fileChioce.SetPath(self.filePath)   # 默认文件路径为 D:\
        bSizer2.Add(self.fp_fileChioce, 0, wx.ALL |
                    wx.ALIGN_CENTER_VERTICAL, 5)

        bSizer1.Add(bSizer2, 1, wx.EXPAND, 5)

        bSizer3 = wx.BoxSizer(wx.HORIZONTAL)

        self.lb_number = wx.StaticText(
            self, wx.ID_ANY, u"每组人数：", wx.DefaultPosition, wx.DefaultSize, 0)
        self.lb_number.Wrap(-1)

        bSizer3.Add(self.lb_number, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        self.tc_number = wx.TextCtrl(
            self, wx.ID_ANY, "5", wx.DefaultPosition, wx.DefaultSize, 0)
        bSizer3.Add(self.tc_number, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        self.lb_groupMethon = wx.StaticText(
            self, wx.ID_ANY, u"请选择分组方式：", wx.DefaultPosition, wx.DefaultSize, 0)
        self.lb_groupMethon.Wrap(-1)

        bSizer3.Add(self.lb_groupMethon, 0, wx.ALL |
                    wx.ALIGN_CENTER_VERTICAL, 5)

        self.rbtn_random = wx.RadioButton(
            self, wx.ID_ANY, u"完全随机", wx.DefaultPosition, wx.DefaultSize, 0)
        self.rbtn_random.SetValue(True)
        bSizer3.Add(self.rbtn_random, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        self.rbtn_manual = wx.RadioButton(
            self, wx.ID_ANY, u"手工设置", wx.DefaultPosition, wx.DefaultSize, 0)
        bSizer3.Add(self.rbtn_manual, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        self.btn_group = wx.Button(
            self, wx.ID_ANY, u"开始分组", wx.DefaultPosition, wx.DefaultSize, 0)
        bSizer3.Add(self.btn_group, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        bSizer1.Add(bSizer3, 1, wx.EXPAND, 5)

        bSizer4 = wx.BoxSizer(wx.HORIZONTAL)

        self.lb_aClass = wx.StaticText(
            self, wx.ID_ANY, u"A类：", wx.DefaultPosition, wx.DefaultSize, 0)
        self.lb_aClass.Wrap(-1)

        bSizer4.Add(self.lb_aClass, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        self.tc_aClass = wx.TextCtrl(
            self, wx.ID_ANY, '2', wx.DefaultPosition, wx.DefaultSize, 0)
        bSizer4.Add(self.tc_aClass, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        self.lb_bClass = wx.StaticText(
            self, wx.ID_ANY, u"B类：", wx.DefaultPosition, wx.DefaultSize, 0)
        self.lb_bClass.Wrap(-1)

        bSizer4.Add(self.lb_bClass, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        self.tc_bClass = wx.TextCtrl(
            self, wx.ID_ANY, '2', wx.DefaultPosition, wx.DefaultSize, 0)
        bSizer4.Add(self.tc_bClass, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        self.lb_cClass = wx.StaticText(
            self, wx.ID_ANY, u"C类：", wx.DefaultPosition, wx.DefaultSize, 0)
        self.lb_cClass.Wrap(-1)

        bSizer4.Add(self.lb_cClass, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        self.tc_cClass = wx.TextCtrl(
            self, wx.ID_ANY, '1', wx.DefaultPosition, wx.DefaultSize, 0)
        bSizer4.Add(self.tc_cClass, 0, wx.ALL | wx.ALIGN_CENTER_VERTICAL, 5)

        bSizer1.Add(bSizer4, 1, wx.EXPAND, 5)

        bSizer5 = wx.BoxSizer(wx.VERTICAL)

        self.tc_result = wx.TextCtrl(self, wx.ID_ANY, u"分组结果。\n1、打开记事本\n2、将学生名单存于记事本中，每行一个，然后保存为文本文件，建议按学生成绩降序排列\n3、单击“选择文件”按钮，选中保存有学生名单的文本文件\n4、填写每组人数\n5、选择分组方式，包括完全随机或手工设置。如选择手工需要设定每类学生人数\n6、A类指成绩较好的学生，B类指成绩一般的学生，C类指后进学生，\n7、设置完毕后，单击“开始分组”按钮即可进行分组",
                                     wx.DefaultPosition, wx.Size(600, 200), wx.TE_MULTILINE)
        bSizer5.Add(self.tc_result, 0, wx.ALL, 5)

        bSizer1.Add(bSizer5, 1, wx.EXPAND, 5)

        self.SetSizer(bSizer1)
        self.Layout()

        self.Centre(wx.BOTH)

        # Connect Events
        self.fp_fileChioce.Bind(wx.EVT_FILEPICKER_CHANGED, self.choiceFile)
        self.rbtn_random.Bind(wx.EVT_RADIOBUTTON, self.randomM)
        self.rbtn_manual.Bind(wx.EVT_RADIOBUTTON, self.manualM)
        self.btn_group.Bind(wx.EVT_BUTTON, self.group)

    def __del__(self):
        pass

    # Virtual event handlers, overide them in your derived class
    # 文件选择按钮事件函数，获取选择的文件的路径
    def choiceFile(self, event):
        self.filePath = self.fp_fileChioce.GetPath()

    # 随机分组 单选按钮事件函数，将randomFlag设置为True
    def randomM(self, event):
        self.randomFlag = True

    # 手工分组 单选按钮事件函数，将randomFlag设置为False
    def manualM(self, event):
        self.randomFlag = False
    # 分组按钮事件函数

    def group(self, event):
        # 检测文件的编码格式
        temp_file=open(self.filePath,'rb');
        content=temp_file.read()
        en=chardet.detect(content).get('encoding')
        temp_file.close()

        file = open(self.filePath,encoding=en)
        self.students = file.readlines()  # 读取所有的信息到列表中
        self.npg = int(self.tc_number.GetValue())  # 读取每组人数
        temp = list()  # 保存一组成员信息的临时列表
        size = len(self.students)   # 列表长度，即学生人数
        if self.randomFlag:
            # 完全随机分组
            random.shuffle(self.students)   # 打乱列表顺序
            pos = 0  # 记录切片时的结束位置
            while pos < size:  # 如果pos >= size，说明划片的起点已经越界了
                # 只要pos小于size -self.npg，就说明还能再分出至少一组
                if pos < size - self.npg:
                    temp = self.students[pos:pos+self.npg]
                    pos += self.npg
                else:  # 如果pos >= size -self.npg，说明剩下的人数正好能再分一组或不足一组
                    temp = self.students[pos:]
                    pos += self.npg
                self.result.append(temp)
        else:
            numberA = int(self.tc_aClass.GetValue())
            numberB = int(self.tc_bClass.GetValue())
            numberC = int(self.tc_cClass.GetValue())   # 获取三类学生的每组人数

            if(numberA+numberB+numberC == self.npg):
                # 将学生按比例分为三类，放入三个列表，并打乱
                a = self.students[0:(numberA*size//self.npg)]
                b = self.students[(numberA*size//self.npg)
                                   :((numberA+numberB)*size//self.npg)]
                c = self.students[((numberA+numberB)*size//self.npg):]
                random.shuffle(a)
                random.shuffle(b)
                random.shuffle(c)
                x = 0
                y = 0
                z = 0
                # 循环，每次从a，b，c三个列表中选取指定数量的学生添加到temp中，直到某个列表空了为止

                while len(a) >= numberA and len(b) >= numberB and len(c) >= numberC:
                    while x < numberA:
                        temp.append(a[0])
                        del a[0]
                        x += 1
                    while y < numberB:
                        temp.append(b[0])
                        del b[0]
                        y += 1
                    while z < numberC:
                        temp.append(c[0])
                        del c[0]
                        z += 1
                    x = y = z = 0
                    # 将temp加入到列表result中，并清空
                    self.result.append(temp)
                    temp = []
                # 将剩余学生加入到temp中，再添加到result中
                if(len(a)):
                    temp += a
                if(len(b)):
                    temp += b
                if(len(c)):
                    temp += c
                if(len(temp)):
                    self.result.append(temp)
            else:
                # 弹出对话框说明填写错误
                wx.MessageBox("每组人数与各类学生人数之和不相等，请重新输入", '注意！！！',
                              wx.OK | wx.ICON_EXCLAMATION)
        # 将结果显示在文本框中
        # self.tc_result.SetValue('')  # 将文本框中的内容清空

        res = str()
        i = 1
        for res1 in self.result:
            res += "第"+str(i)+"组：\n"
            i += 1
            for res2 in res1:
                res += res2
            res += "\n"

        self.tc_result.SetValue(res)
        self.result = []  # 每次按下按钮后需要将其清空，否则多次按下分组按钮会多次读取数据累加到之前的结果中
        file.close()   # 关闭文件


# 启动窗口
if __name__ == "__main__":
    app = wx.App()
    frame1 = MyFrame1()
    frame1.Show()
    app.MainLoop()
