package org.geeksword.xwy.swordoffer;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;


public class ListNode<E> {
    E val;
    private ListNode next;

    //无参构造方法
    ListNode() {

    }

    //带参构造方法
    private ListNode(E e) {
        this.val = e;
    }

    //头节点
     ListNode head = null;


    /**
     * 反转链表
     * A->B->C->D 转为 A<-B<-C<-D
     */
    public void reserveLink(){
        ListNode curNode = head;//头结点
        ListNode preNode = null;//前一个结点
        while(curNode != null){
            ListNode nextNode = curNode.next;//保留下一个结点
            curNode.next = preNode;//指针反转
            preNode = curNode;//前结点后移
            curNode = nextNode;//当前结点后移
        }
        head = preNode;
    }



    /**
     * 链表添加结点:
     * 找到链表的末尾结点，把新添加的数据作为末尾结点的后续结点
     *
     * @param data
     */
    public void addNode(int data) {
        ListNode<Integer> newNode = new ListNode<Integer>(data);
//        newNode.val = data;
        if (head == null) {
            head = newNode;
            return;
        }
        ListNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }

    /**
     * 打印结点
     */
    public void printLink() {
        ListNode curNode = head;
        while (curNode != null) {
            System.out.print(curNode.val + " ");
            curNode = curNode.next;
        }
        System.out.println();
    }

    //反向打印链表 递归
    public ArrayList<Integer> printListFromTailToHead(ListNode<Integer> listNode){
        ArrayList<Integer> ret = new ArrayList<Integer>();
        if (listNode != null){
            ret.addAll(printListFromTailToHead(listNode.next));
            ret.add(listNode.val);
        }
        return ret;
    }
}
