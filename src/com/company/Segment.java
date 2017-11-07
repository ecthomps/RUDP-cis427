package com.company;

import java.io.Serializable;

class Segment implements Serializable {
    int next = 0;
    int seqAckNum;
    byte[] data;
    private static final long serialVersionUID = 1L;
}
