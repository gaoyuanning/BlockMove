# -*- coding: utf-8 -*-
"""
Created on Tue Mar 15 11:33:00 2016

@author: Eric
"""
import time
import operator
import copy
#import numpy
class node:
    def __init__(self,name=None):
        self.name=name
        self.path=[]
        self.price=0
def moveBlock(s,n):
    num=s.find('E')
    if num-3<0:
        low=0
    else:
        low=num-3
    if num+3>2*n:
        top=2*n
    else:
        top=num+3
    res=[]
    price=[]
    for i in range(low,top+1):
        if i!=num:
            temp=list(s)
            temp[i]=s[num]
            temp[num]=s[i]
            res.append("".join(temp))
            if abs(num-i)>2:
                price.append(2)
            else:
                price.append(1)
    return res,price
'''def judgeBlock(s,n):
    error=0
    num=s.find('E')
    if num>n:
        mid='B'
    elif num<n:
        mid='W'
    else :
        mid='E'
    for i in range(n):
        if s[i]=='B':
            error+=1
    if s[n]!=mid:
        error+=1
    for i in range(n+1,2*n+1):
        if s[i]=='W':
            error+=1
    return 2*error'''

def getNode(s,d):
    k=d.keys()
    for i in k:
        if s==i.name:
            return i
def getName(l):
    res=[]
    for i in l:
        res.append(i.name)
    return res
def todict(op):
    #print op
    temp={}
    for i in op:
        #print i[0],i[1]
        temp[i[0]]=i[1]
    #print temp
    return temp
def solveBlock(s,length):
    op={}
    cl={}
    tempNode=node(s)
    tempNode.path=[s]
    op[tempNode]=judgeBlock(tempNode.name,length)
    #x=0
    while(op!={}):
        sortedict=sorted(op.iteritems(),key=operator.itemgetter(1))
        k=sortedict[0][0]
        op=todict(sortedict)
        if judgeBlock(k.name,length)==0:
            print 'result:',k.name
            print 'path:',k.path
            print 'price:',k.price
            print 'steps:',len(k.path)-1
            return k
        flag=False
        for i in cl.keys():
            if i.name==k.name:
                if cl[i]>op[k]:
                    del(cl[i])
                    cl[k]=op[k]                    
                    del(op[k])
                    flag=True
                    break
        if not flag:
            cl[k]=op[k]
            del(op[k])
        res,price=moveBlock(k.name,length)
        pri=[]
        for i in range(len(price)):
            #pri.append(price[i]+cl[k])#yuanban
            pri.append((price[i]+k.price+judgeBlock(res[i],length)))
        for i in range(len(res)):     
            if res[i] in getName(op.keys()) :
                tNode=getNode(res[i],op)
                if pri[i]<op[tNode]:
                    nNode=node(res[i])
                    nNode.path=copy.copy(k.path)
                    nNode.path.append(res[i])
                    nNode.price=price[i]+k.price
                    if judgeBlock(nNode.name,length)==0:
                        print 'result:',nNode.name
                        print 'path:',nNode.path
                        print 'price:',nNode.price
                        print 'steps:',len(nNode.path)-1
                        return nNode
                    #print k.path
                    #print nNode.path,nNode.name
                    #op[nNode]=price[i]+judgeBlock(res[i],length)
                    #op[nNode]=price[i]+k.price+judgeBlock(res[i],length)
                    del(op[tNode])
                    #op[nNode]=pri[i]+judgeBlock(res[i],length)#yuanban
                    op[nNode]=pri[i]
                    
            elif res[i] not in getName(op.keys()):
                nNode=node(res[i])
                nNode.path=copy.copy(k.path)
                nNode.path.append(res[i])
                nNode.price=price[i]+k.price
                if judgeBlock(nNode.name,length)==0:
                        print 'result:',nNode.name
                        print 'path:',nNode.path
                        print 'price:',nNode.price
                        print 'steps:',len(nNode.path)-1
                        return nNode
                #print k.path
                #print nNode.path,nNode.name
                #op[nNode]=price[i]+judgeBlock(res[i],length)
                #op[nNode]=pri[i]+judgeBlock(res[i],length)#yuanban
                op[nNode]=pri[i]

def example():
    res=[]
    temp=['B','B','B','B','B','B','B']
    for i in range(7):
        for j in range(i+1,7):
            for k in range(j+1,7):
                if i!=j and j!=k and i!=k:
                    temp[i]='W'
                    temp[j]='W'
                    temp[k]='W'
                    num=numpy.where(numpy.array(temp)!='W')
                    for n in num[0]:
                        t=copy.copy(temp)
                        t[n]='E'
                        res.append(''.join(t))
                    temp=['B','B','B','B','B','B','B']
    return res
def judgeBlock(s,length):
    statistic=[]
    for i in range(2*length+1):
        if s[i]=='E':
            continue
        if statistic==[]:
           statistic.append([s[i],1])
           continue
        else:
            if s[i]==statistic[-1][0]:
                statistic[-1][1]+=1
                continue
            else:
                statistic.append([s[i],1])
    summation=0
    error=0
    for i in statistic:
        if i[0]=='B':
            summation+=i[1]
        else:
            error+=i[1]*summation
    return int(error*1.5)
if __name__=='__main__':
    #print judge('BWWWBBE',7)    
    '''f=open('res.txt','w')
    res=example()  
    a=set(res)
    for s in a:
        k=solveBlock(s,len(s)/2)
        print s,k.price
        temp=s+'        '+str(k.price)+'        '+str(int(judge(s,len(s))*1.5))+'\n'
        f.write(temp)
    f.close()'''
    start=time.time()
    block='BBBBBWBWWWWWE'
    k=solveBlock(block,len(block)/2)
    end=time.time()
    print 'sec:%fs' % (end-start)
    #res,price=moveBlock('EWWBBBW',3)
    #print res
    
    
