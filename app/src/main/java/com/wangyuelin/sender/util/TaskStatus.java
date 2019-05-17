package com.wangyuelin.sender.util;

public interface TaskStatus {
    int WAIT_SEND = 1; //等待配送
    int SENDING = 2; //正在配送
    int SENDED = 3;//已配送
}
