package com.ruyicai.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jivesoftware.smack.packet.Message.Type;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.inject.Inject;
import com.ruyicai.component.MsgPopMenu;
import com.ruyicai.component.view.AbstractMessageView;
import com.ruyicai.component.view.ChatListView.OnRefreshListener;
import com.ruyicai.component.view.SimpleMessageView;
import com.ruyicai.component.view.SimpleMessageView.OnMessageEditListener;
import com.ruyicai.controller.service.MessageService;
import com.ruyicai.data.db.DbHelper;
import com.ruyicai.model.HttpUser;
import com.ruyicai.model.MessageStatus;
import com.ruyicai.model.MyMessage;
import com.ruyicai.model.PayLoadBean;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.StringUtils;
import com.ruyicai.util.date.MyDate;
import com.ruyicai.util.json.JsonUtils;
import com.ruyicai.xmpp.XmppService;

public class ChattingListViewAdapter extends BaseAdapter implements OnMessageEditListener/*,OnSendImageMessageListener*/{
	private int IMVT_RIGHT_MSG = 0;// 自己的
	private int IMVT_LEFT_MSG = 1;// 别人的
	private int IMVT_RIGHT_LINK_MSG = 2;// 自己的连接
	private int IMVT_LEFT_LINK_MSG = 3;// 别人的连接
	private int IMVT_LEFT_IMAGE_MSG = 4;// 别人的图片
	private int IMVT_RIGHR_IMAGE_MSG = 5;// 别人的图片
	private List<MyMessage> listData=new ArrayList<MyMessage>();
	private OnRefreListView onRefreListView;//
	private OnRefreshListener listViewListener;//ListView 的刷新加载接口
	@Inject private Context context;
//	@InjectResource(R.drawable.man_icon) Drawable defaultIcon; 
//	@Inject UserService userService;
	@Inject MsgPopMenu msgPopMenu;
	@Inject DbHelper dbHelper;
	@Inject XmppService xmppService;
	@Inject MessageService messageService;
//	@Inject FaceService faceService;
//	private ImageShowUtils imageShowUtils;
	private static final int PAGE_NUM=10;
	
//	public ChattingListViewAdapter(Context context) {
//		this.context = context;
//	}
	
//	public ImageShowUtils getImageShowUtils() {
//		return imageShowUtils;
//	}
//	public void setImageShowUtils(ImageShowUtils imageShowUtils) {
//		this.imageShowUtils = imageShowUtils;
//	}
	public void initData(List<MyMessage> messages) {
		this.listData=messages;
	}
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
	public List<MyMessage> getListData() {
		return listData;
	}
	@Override
	public int getCount() {
		return listData.size();
	}
	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void addMessage(MyMessage myMessage){
		listData.add(myMessage);
		sortMessage(listData);
		notifyDataSetChanged();
	}
	public void addMessage(List<MyMessage> myMessages){
		listData.addAll(myMessages);
		sortMessage(listData);
		notifyDataSetChanged();
	}
	public void sortMessage(List<MyMessage> listData){
		Collections.sort(listData, new Comparator<MyMessage>() {
			@Override
			public int compare(MyMessage lhs, MyMessage rhs) {
				if(lhs.getReceiveTime()==null||rhs.getReceiveTime()==null){
					return 1;
				}
				return Long.valueOf(lhs.getReceiveTime().getTime()).compareTo(Long.valueOf(rhs.getReceiveTime().getTime()));
			}
		});
	}
	public void setIXListViewListener(OnRefreshListener listViewListener){
		this.listViewListener=listViewListener;
	}
	public void setOnRefreListView(OnRefreListView onRefreListView){
		this.onRefreListView=onRefreListView;
	}
	private int getViewType(MyMessage msg){
		if (msg.getFrom().equals(HttpUser.userId)) {
			return getRight(msg);
		} 
		return getLeft(msg);
	}
	
	/**
	 * 右边消息的View类型
	 * @param msg
	 * @return
	 */
	private int getRight(MyMessage msg){
		int rightType=IMVT_RIGHT_MSG;
		switch (getMsgType(msg)) {
		case NORMALCHAT:
			rightType=IMVT_RIGHT_MSG;
			break;
		case DYNAMICSHARE:
			rightType=IMVT_RIGHT_LINK_MSG;
			break;
		case IMAGE:
			rightType=IMVT_RIGHR_IMAGE_MSG;
			break;
		}
		return rightType;
	}
	/**
	 * 左边消息的View类型
	 * @param msg
	 * @return
	 */
	private int getLeft(MyMessage msg){
		int leftType=IMVT_LEFT_MSG;
		switch (getMsgType(msg)) {
		case NORMALCHAT:
			leftType=IMVT_LEFT_MSG;
			break;
		case DYNAMICSHARE:
			leftType=IMVT_LEFT_LINK_MSG;
			break;
		case IMAGE:
			leftType=IMVT_LEFT_IMAGE_MSG;
			break;
		}
		return leftType;
	}
	@Override
	public int getItemViewType(int position) {
		MyMessage msg = listData.get(position);
		return getViewType(msg);
	}
	@Override
	public int getViewTypeCount() {
		return 6;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final MyMessage msg = listData.get(position);
		return getSimpleView(convertView, msg);
	}
	private View getSimpleView(View convertView, MyMessage msg){
		if(convertView==null){
			return getMessageView(msg);
		}
		AbstractMessageView abstractMessageView=(AbstractMessageView) convertView;
//		abstractMessageView.setMessage(msg, userService.getContactsUserInfoByUserId(msg.getFrom()));
//		abstractMessageView.setMsgContent(faceService.analysisFace(context, msg.getBody()));
		abstractMessageView.setStatus(msg.getStatus());
		abstractMessageView.setTime(MyDate.getDateFromLong(msg.getMsgTime()));
		abstractMessageView.setMsgDetailBtnListener(new MsgDetailBtnListener(msg));
		return abstractMessageView;
	}
	/**
	 * 根据消息类型使用对应的View
	 * @param msg
	 * @return
	 */
	private View getMessageView(MyMessage msg) {
		View view=createMessageView(msg);
		switch (getMsgType(msg)) {
		case NORMALCHAT:
			view=createMessageView(msg);
			break;
		case DYNAMICSHARE:
//			view=createLinkMessageView(msg);
			break;
		case IMAGE:
//			view=createImageMessageView(msg);
			break;
		}
		return view;
	}
	public enum MsgType {
		NORMALCHAT, DYNAMICSHARE, IMAGE;
	}
	public MsgType getMsgType(MyMessage msg){
		if(!StringUtils.isNotEmty(msg.getPayLoad())){
			return MsgType.NORMALCHAT;
		}
		try {
			PayLoadBean payLoadBean=JsonUtils.resultData(msg.getPayLoad(), PayLoadBean.class);
			if("3".equals(payLoadBean.getType())){
				return MsgType.DYNAMICSHARE;
			}else if ("img".equals(payLoadBean.getType())) {
				return MsgType.IMAGE;
			}
			return MsgType.NORMALCHAT;
		} catch (Exception e) {
			return MsgType.NORMALCHAT;
		}
	}
	/**
	 * 正常聊天的消息
	 * @param msg
	 * @return
	 */
	private SimpleMessageView createMessageView(final MyMessage msg) {
		final boolean isLeft = !msg.getFrom().equals(HttpUser.userId);
		SimpleMessageView simpleMessageView = new SimpleMessageView(context,isLeft);
//		simpleMessageView.setMsgContent(faceService.analysisFace(context, msg.getBody()));
//		simpleMessageView.setMessage(msg,userService.getContactsUserInfoByUserId(msg.getFrom()));
		simpleMessageView.setEnabled(true);
		simpleMessageView.setOnResendListener(this);
		simpleMessageView.setMsgDetailBtnListener(new MsgDetailBtnListener(msg));
		return simpleMessageView;
	}
	/**
	 * 分享动态的消息
	 * @param msg
	 * @return
	 */
//	private LinkMessageView createLinkMessageView(final MyMessage msg) {
//		final boolean isLeft = !msg.getFrom().equals(HttpUser.userId);
//		LinkMessageView linkMessageView = new LinkMessageView(context,isLeft);
//		linkMessageView.setMessage(msg,userService.getContactsUserInfoByUserId(msg.getFrom()));
//		linkMessageView.setEnabled(true);
//		linkMessageView.setOnResendListener(this);
//		linkMessageView.setMsgDetailBtnListener(new MsgDetailBtnListener(msg));
//		return linkMessageView;
//	}
	/**
	 * 发图片的消息
	 * @param msg
	 * @return
	 */
//	private ImageMessageView createImageMessageView(MyMessage msg) {
//		boolean isLeft = !msg.getFrom().equals(HttpUser.userId);
//		ImageMessageView imageMessageView = new ImageMessageView(context,isLeft);
//		imageMessageView.setMessage(msg,userService.getContactsUserInfoByUserId(msg.getFrom()));
//		imageMessageView.setEnabled(true);
//		imageMessageView.setOnResendListener(this);
//		imageMessageView.setOnSendImageMessageListener(this);
//		imageMessageView.setMsgDetailBtnListener(new MsgDetailBtnListener(msg));
//		return imageMessageView;
//	}
	/**
	 * 消息操作
	 * @author SKS
	 *
	 */
	class MsgDetailBtnListener implements OnLongClickListener{
		MyMessage msg;
		public MsgDetailBtnListener(MyMessage msg) {
			this.msg=msg;
		}
		@Override
		public boolean onLongClick(View v) {
			msgPopMenu.showMsgPopMenu(context);
			msgPopMenu.setMessage(msg,getMsgContent(msg));
			msgPopMenu.setOnMessageEditListener(ChattingListViewAdapter.this);
			msgPopMenu.showAsDropDown(v);
			return false;
		}
	}
	/**
	 * 获得需要复制的消息内容
	 * @param msg
	 * @return
	 */
	public String getMsgContent(MyMessage msg){
		if(!StringUtils.isNotEmty(msg.getPayLoad())){
			return msg.getBody();
		}
		try {
			PayLoadBean payLoadBean=JsonUtils.resultData(msg.getPayLoad(), PayLoadBean.class);
			if(payLoadBean.getType().equals("3")){
				return payLoadBean.getMsg();
			}
			return msg.getBody();
		} catch (Exception e) {
			return msg.getBody();
		}
	}
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			MyMessage message=(MyMessage)msg.obj;
			int status=msg.what;
			messageSendStatus(message, MessageStatus.getMessageStatusForValue(status));
		}
	};
	public void changMsgStatus(MyMessage myMessage,int status){
		Message msg=handler.obtainMessage();
		msg.what=status;
		msg.obj=myMessage;
		handler.sendMessage(msg);
	}
	/**
	 * 刷新消息状态
	 * @param myMessage
	 * @param msgStatus
	 */
	public void messageSendStatus(MyMessage myMessage,MessageStatus msgStatus){
		for(MyMessage message:this.listData){
			if(message.getId().equals(myMessage.getId())){
				message.setStatus(msgStatus);
				this.notifyDataSetChanged();
			}
		}
	}
	/**
	 * 载入消息
	 * @param userId
	 * @param pageIndex
	 */
	public void loadMoreMsg(String userId,int pageIndex){
		new LoadMoreMessage(pageIndex).execute(userId);
	}
	class LoadMoreMessage extends AsyncTask<String, Void, List<MyMessage>>{
		int pageI;
		public LoadMoreMessage(int pageI) {
			this.pageI=pageI;
		}
		@Override
		protected List<MyMessage> doInBackground(String... params) {
			return null;
//			return dbHelper.selectAllMsgByUserNameAndMyName(params[0],pageI,PAGE_NUM);
		}
		@Override
		protected void onPostExecute(List<MyMessage> result) {
			if(result==null||result.size()==0){
				onRefreListView.refreListView(0);
				return;
			}
			userReadAllMsg(result);
			addMessage(result);
			notifyDataSetChanged();
			onRefreListView.refreListView(1);
		}
	}
	/**
	 * 重发
	 */
	@Override
	public void onResend(MyMessage myMessage) {
//		dbHelper.upDateMsgStatus(myMessage.getId(),MessageStatus.Sending.getValue());
		changMsgStatus(myMessage,MessageStatus.Sending.getValue());
		try {
			myMessage.setType(Type.chat);
			xmppService.sendMsg(myMessage);
			messageService.beforeSendMessage(myMessage);
		} catch (Exception e) {
			messageService.messageSendFail(myMessage);
		}
	}
	/**
	 * 删除
	 */
	@Override
	public void onDelete(MyMessage myMessage) {
//		dbHelper.deleteOneMsgByMsgID(myMessage.getId());
		listData.remove(myMessage);
		if(listData.size()==0){
			listViewListener.onRefresh();
		}
		notifyDataSetChanged();
	}
	public interface OnRefreListView{
		public void refreListView(int status);
	}
	/**
	 * 读取消息 反馈
	 * @param listMsg
	 */
	private void userReadAllMsg(List<MyMessage> listMsg){
		if(listMsg==null||listMsg.size()==0){
			return;
		}
		for(MyMessage myMessage:listMsg){
			if(myMessage.getStatus().getValue()==MessageStatus.UserReceived.getValue()){
				if (PublicMethod.isAppRunning(context)) {
					userReadOneMsg(myMessage);
				}
			}else if (myMessage.getStatus().getValue()==MessageStatus.Sending.getValue()) {
				this.onResend(myMessage);
			}
		}
	}
	public void userReadOneMsg(MyMessage myMessage){
//		dbHelper.upDateMsgStatus(myMessage.getId(), MessageStatus.UserRead.getValue());
		xmppService.sendMsg(getMessageToSend(myMessage));
	}
	private MyMessage getMessageToSend(MyMessage myMessage) {
		return messageService.createMessage(myMessage.getFrom(),HttpUser.userId, messageService.createMsgBody(myMessage.getId(),"Displayed"),"msgStatus",Type.normal,myMessage.getId());
	}
	
	/**
	 * 发送图片
	 */
//	@Override
//	public void onSendImage(MyMessage myMessage,String imageId) {
//		PayLoadBean payLoadBean=JsonUtils.resultData(myMessage.getPayLoad(), PayLoadBean.class);
//		String payLoad=messageService.createPayLoad(payLoadBean.getThumb(),imageId, "img");
//		myMessage.setPayLoad(payLoad);
//		dbHelper.upDateMsgPayLoad(myMessage.getId(), payLoad);
//		myMessage.setMsgTime(String.valueOf(new Date().getTime()));
//		this.onResend(myMessage);
//	}
//	@Override
//	public void startUploadImage(MyMessage myMessage) {
//		dbHelper.upDateMsgStatus(myMessage.getId(), MessageStatus.Uploading.getValue());
//		changMsgStatus(myMessage,MessageStatus.Uploading.getValue());
//	}
//	@Override
//	public void failureUploadImage(MyMessage myMessage) {
//		dbHelper.upDateMsgStatus(myMessage.getId(), MessageStatus.UploadFaile.getValue());
//		changMsgStatus(myMessage,MessageStatus.UploadFaile.getValue());
//	}
}



