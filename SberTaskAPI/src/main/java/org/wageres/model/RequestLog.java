package org.wageres.model;

public class RequestLog
{
    private long processTime;
    private String requestAction;
    private String userAddress;
    private String resourceIdentifier;
    private int Responsestatus;

    public RequestLog()
    {

    }

    public RequestLog(long processTime,String requestAction,
                      String userAddress,String resourceIdentifier,
                      int responsestatus)
    {
        this.processTime = processTime;
        this.requestAction = requestAction;
        this.userAddress = userAddress;
        this.resourceIdentifier = resourceIdentifier;
        this.Responsestatus = responsestatus;
    }

    /*1)processTime*/
    public void setProcessTime(long processTime)
    {
        this.processTime = processTime;
    }

    public long getProcessTime()
    {
        return processTime;
    }

    /*2)requestAction*/
    public void setRequestAction(String requestAction)
    {
        this.requestAction = requestAction;
    }

    public String getRequestAction()
    {
        return requestAction;
    }

    /*3)userAddress*/
    public void setUserAddress(String userAddress)
    {
        this.userAddress = userAddress;
    }

    public String getUserAddress()
    {
        return userAddress;
    }

    /*4)resourceIdentifier*/
    public void setResourceIdentifier(String resourceIdentifier)
    {
        this.resourceIdentifier = resourceIdentifier;
    }

    public String getResourceIdentifier()
    {
        return resourceIdentifier;
    }

    /*5)responsestatus*/
    public void setResponsestatus(int responsestatus)
    {
        Responsestatus = responsestatus;
    }

    public int getResponsestatus()
    {
        return Responsestatus;
    }
}
