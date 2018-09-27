package com.xl.game.tool;

public class FilesInfo
{
    public int mFileNum=0;
    public int mDirNum=0;
    public int mUnknowNum=0;
    public long mSize=0;
    public void Add(FilesInfo info)
    {
        mFileNum+=info.mFileNum;
        mDirNum+=info.mDirNum;
        mSize+=info.mSize;
        mUnknowNum+=info.mUnknowNum;
    }
}
