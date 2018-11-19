pcb = []


def FCFS():
    pcb.sort(key=lambda x: x[1], reverse=False)
    for i in range(n):
        if (i == 0):
            startTime = int(pcb[i][1])
            pcb[i][3] = startTime
            pcb[i][4] = startTime + int(pcb[i][2])

        elif (i > 0 and int(pcb[i - 1][4]) < int(pcb[i][1])):
            startTime = int(pcb[i][1])
            pcb[i][3] = startTime
            pcb[i][4] = startTime + int(pcb[i][2])

        else:
            startTime = pcb[i - 1][4]
            pcb[i][3] = startTime
            pcb[i][4] = startTime + int(pcb[i][2])

        i += 1
    for i in range(n):
        pcb[i][5] = float(pcb[i][4] - int(pcb[i][1]))
        pcb[i][6] = float(pcb[i][5] / int(pcb[i][2]))
        i += 1
    SzzTime = 0
    SdqzzTime = 0
    for i in range(n):
        SzzTiinse = float(SzzTime + float(pcb[i][5]))
        SdqzzTime = float(SdqzzTime + float(pcb[i][6]))
        AzzTime = float(SzzTime / n)
        AdqzzTime = float(SdqzzTime / n)
    pcb.sort(key=lambda x: x[3], reverse=False)
    print("运行结果:")
    for i in range(n):
        print("时刻: %d 开始运行进程: %s    完成时间: %d 周转时间: %d 带权周转时间: %.2f" \
              % (int(pcb[i][3]), pcb[i][0], int(pcb[i][4]), int(pcb[i][5]), float(pcb[i][6])))
        i += 1
    print("本次调度的平均周转时间为： %.2f" % float(AzzTime))
    print("本次调度的平均带权周转时间为： %.2f" % float(AdqzzTime))

def SJF():
    sjfPcb = pcb
    i = 1
    k = 0
    sjfPcb.sort(key=lambda x: x[1], reverse=False)

    startTime0 = int(sjfPcb[0][1])
    pcb[0][3] = startTime0
    pcb[0][4] = startTime0 + int(sjfPcb[0][2])
    sjfPcb[0][5] = int(sjfPcb[0][4]) - int(sjfPcb[0][1])
    sjfPcb[0][6] = float(sjfPcb[0][5]) / int(sjfPcb[0][2])


    temp_pcb = sjfPcb[1:len(sjfPcb)]
    temp_pcb.sort(key=lambda x: x[2], reverse=False)
    sjfPcb[1:len(sjfPcb)] = temp_pcb



    while (i < n):
        h = 1
        while (int(sjfPcb[i][1]) >= int(sjfPcb[i - 1][4])):
            if (i == n - 1):
                startTime = sjfPcb[i][1]
                sjfPcb[i][3] = startTime
                sjfPcb[i][4] = startTime + int(sjfPcb[i][2])
                sjfPcb[i][5] = int(sjfPcb[i][4]) - int(sjfPcb[i][1])
                sjfPcb[i][6] = float(sjfPcb[i][5]) / int(sjfPcb[i][2])
                k = 1
                break
            else:
                temp_sjfPcb = sjfPcb[i]
                sjfPcb[i] = sjfPcb[i + h]
                sjfPcb[i + h] = temp_sjfPcb
                h += 1
                if (h >= n - i - 1):
                    temp_pcb2 = sjfPcb[i:len(sjfPcb)]
                    temp_pcb2.sort(key=lambda x: x[1], reverse=False)
                    sjfPcb[i:len(sjfPcb)] = temp_pcb2

                    sjfPcb[i][3] = int(sjfPcb[i][1])
                    sjfPcb[i][4] = int(sjfPcb[i][1]) + int(sjfPcb[i][2])
                    sjfPcb[i][5] = int(sjfPcb[i][4]) - int(sjfPcb[i][1])
                    sjfPcb[i][6] = float(sjfPcb[i][5]) / int(sjfPcb[i][2])

                    temp_pcb2 = sjfPcb[i + 1:len(sjfPcb)]
                    temp_pcb2.sort(key=lambda x: x[2], reverse=False)
                    sjfPcb[i + 1:len(sjfPcb)] = temp_pcb2
                    h = 1
                    i += 1
                else:
                    continue
        if (k == 1):
            break
        else:
            startTime = sjfPcb[i - 1][4]
            sjfPcb[i][3] = startTime
            sjfPcb[i][4] = startTime + int(sjfPcb[i][2])
            sjfPcb[i][5] = int(sjfPcb[i][4]) - int(sjfPcb[i][1])
            sjfPcb[i][6] = float(sjfPcb[i][5]) / int(sjfPcb[i][2])

            i += 1
    SzzTime = 0
    SdqzzTime = 0
    for i in range(n):
        SzzTime = float(SzzTime + float(pcb[i][5]))
        SdqzzTime = float(SdqzzTime + float(pcb[i][6]))
        AzzTime = float(SzzTime / n)
        AdqzzTime = float(SdqzzTime / n)
    sjfPcb.sort(key=lambda x: x[3], reverse=False)
    print("运行结果:")
    for i in range(n):
        print("时刻: %d 开始运行进程: %s    完成时间: %d 周转时间: %d 带权周转时间: %.2f" \
              % (int(sjfPcb[i][3]), sjfPcb[i][0], int(sjfPcb[i][4]), int(sjfPcb[i][5]), float(sjfPcb[i][6])))
        i += 1
    print("本次调度的平均周转时间为： %.2f" % float(AzzTime))
    print("本次调度的平均带权周转时间为： %.2f" % float(AdqzzTime))

def inputPCB():
    i = 0
    while (i < n):
        print("##############################################")
        pName = input("请输入第 %d 个进程名：" % (i + 1))
        arriveTime = int(input("请输入到达时间:"))
        serviceTime = int(input("请输入服务时间:"))
        pcb.append([pName, arriveTime, serviceTime, 0, 0, 0, 0])
        i += 1

if __name__ == '__main__':
    n = int(input("请输入进程数："))
    inputPCB()
    print("先来先服务算法FCFS")
    print("最短进程优先算法SJF")
    m = 1
    while (m == 1):
        option = int(input("请输入选择的算法(输入1，2进行选择，其他键退出):"))
        if (option == 1):
            FCFS()
        elif (option == 2):
            SJF()
        else:
            print("退出计算")
            m = 0
