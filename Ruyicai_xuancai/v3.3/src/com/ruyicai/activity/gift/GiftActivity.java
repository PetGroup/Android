/**
 * 
 */
package com.ruyicai.activity.gift;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Contacts.People;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.BufferType;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.constant.Constants;
import com.ruyicai.dialog.BaseDialog;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.net.newtransaction.GiftMessageInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.PublicMethod;

/**
 * @author Administrator
 * 
 */
public class GiftActivity extends Activity implements HandlerMsg {
	private ImageButton language, phone;
	private Button ok, cancel;
	private EditText editLanguage, editPhone;
	private TextView zhuma, zhushu;
	private ListView avdiceList;
	public BetAndGiftPojo betAndGift;// Ͷע��Ϣ��
	private String zhumaStr;
	public final static String TITLE = "TITLE"; /* ���� */
	AlertDialog adviceDialog;
	public final static String INFO = "INFO"; /* ��Ϣ */
	List<Map<String, Object>> list;/* �б�������������Դ */
	
	ProgressDialog progressdialog;
	MyHandler handler = new MyHandler(this);// �Զ���handler
	Handler handlerTwo = new Handler();
	private Vector<Person> persons = new Vector<Person>();// ������ϵ��
	private Vector<Person> checkedPersons = new Vector<Person>();// ѡ����ϵ��
	List<String> successPersons = new ArrayList();//���ͳɹ��ĵ绰����
	// private Vector<String> checkedState = new Vector<String>();// ѡ��״̬
	private boolean[] checkedState;// ѡ��״̬
	String phoneStr;
	String languageStr;
	String message;
	JSONObject obj;
    boolean isMsg = true;//�Ƿ��ǻ�ȡ����
    String giftMsg;
	int allAtm;
	int zhu;
	boolean isDialog = false;
	private int restrictMax = 8;
	private int endMax = 8;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_gift);
		init();
		getInfo();
		setValue();

	}

	/**
	 * ��ʼ�����
	 */
	public void init() {
		isDialog = false;
		Button imgRetrun = (Button) findViewById(R.id.layout_gift_img_return);
		imgRetrun.setBackgroundResource(R.drawable.returnselecter);
		// ImageView�ķ����¼�
		imgRetrun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		editLanguage = (EditText) findViewById(R.id.gift_edit_language);
		editLanguage.setText(languageStr);
		editPhone = (EditText) findViewById(R.id.gift_edit_phone);
		ok = (Button) findViewById(R.id.gift_img_ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addPerson();
				if (checkedPersons.size() != 0) {
					beginTouZhu();
				} else {
					Toast.makeText(GiftActivity.this, R.string.ruyichuanqing_atleastone,Toast.LENGTH_SHORT).show();
				}

			}
		});
		cancel = (Button) findViewById(R.id.gift_img_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 finish();
			}
		});
		language = (ImageButton) findViewById(R.id.gift_img_language);
		language.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Constants.avdiceStr==null){
					giftMsgNet();
				}else{
					adviceDialog();
				}
				
			}
		});
		phone = (ImageButton) findViewById(R.id.gift_img_phone);
		phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editPhone.setText("");
				checkedPersons.clear();
				LinkDialog();
			}
		});
		zhuma = (TextView) findViewById(R.id.gift_text_zhuma);
		zhushu = (TextView) findViewById(R.id.gift_text_zhushu);
		editLanguage.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				editLanguage.setFocusable(true);
				return false;
			}
		});
		editPhone.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				editPhone.setFocusable(true);
				return false;
			}
		});

	}

	/**
	 * ������������Ϣ���������
	 */
	public void addPerson() {
		String phone = editPhone.getText().toString();
		if (!phone.equals("")) {
		    phone = phone.replace("��", ",");
			String phones[] = phone.split(",");
			if (checkedPersons.size() == 0) {
				for (int i = 0; i < phones.length; i++) {
					Person person = new Person("", phones[i]);
					checkedPersons.add(person);
				}
			} else if (checkedPersons.size() > phones.length) {
				for (int i = 0; i < checkedPersons.size(); i++) {
					for (int j = 0; j < phones.length; j++) {
						if (checkedPersons.get(i).phone.equals(phones[j])) {
							break;
						} else if (j == phones.length - 1) {
							checkedPersons.remove(i);
						}
					}
				}
			} else if (checkedPersons.size() < phones.length) {
				for (int i = 0; i < phones.length; i++) {
					for (int j = 0; j < checkedPersons.size(); j++) {
						if (phones[i].equals(checkedPersons.get(j).phone)) {
							break;
						} else if (j == checkedPersons.size() - 1) {
							Person person = new Person("", phones[i]);
							checkedPersons.add(person);
						}
					}
				}
			} else if (checkedPersons.size() == phones.length) {
				for (int i = 0; i < phones.length; i++) {
					if (!phones[i].equals(checkedPersons.get(i).phone)) {
						Person person = new Person("", phones[i]);
						checkedPersons.remove(i);
						checkedPersons.add(i, person);
					}
				}
			}
		}
	}

	/**
	 * ��ֵ
	 * 
	 */
	public void setValue() {
		SpannableStringBuilder builder = new SpannableStringBuilder();
		int beishu = Integer.parseInt(betAndGift.getLotmulti());
		allAtm = Integer.parseInt(betAndGift.getAmount()) / 100;
		zhu = allAtm / beishu / betAndGift.getAmt();
		String zhushuStr = "����ע����" + zhu + "ע\n\n" + "���ͱ�����" + beishu + "��\n\n"
				+ "���ͽ�" + allAtm + "Ԫ";
		builder.append(zhushuStr);
		builder.setSpan(new ForegroundColorSpan(Color.RED), zhushuStr.length()
				- (Integer.toString(allAtm).length() + 1), zhushuStr.length(),
				Spanned.SPAN_COMPOSING);
		this.zhushu.setText(builder, BufferType.EDITABLE);
		// this.zhushu.setText(zhushuStr);
		this.zhuma.setText(zhumaStr);

	}

	/**
	 * ����һ��ҳ���ȡ��Ϣ
	 */
	public void getInfo() {
		Intent intent = getIntent();
		byte[] bytes = intent.getByteArrayExtra("info");
		zhumaStr = intent.getStringExtra("zhuma");
		if (bytes != null) {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			try {
				ObjectInputStream objStream = new ObjectInputStream(byteStream);
				betAndGift = (BetAndGiftPojo) objStream.readObject();
			} catch (Exception e) {
			}
		}

	}

	/**
	 * ����ѡ���
	 */
	public void adviceDialog() {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dialog_advice, null);
		adviceDialog = new AlertDialog.Builder(this).create();
		adviceDialog.show();
		TextView title = (TextView) v.findViewById(R.id.alert_dialog_touzhu_text_title);
		title.setText(R.string.ruyichuanqing_normalgreetwords);//������
		avdiceList = (ListView) v.findViewById(R.id.dialog_advice_list);
		avdiceList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				adviceDialog.cancel();
				languageStr = Constants.avdiceStr[position];
				editLanguage.setText(languageStr);

			}
		});
		Button ok = (Button) v.findViewById(R.id.dialog_advice_img_ok);
		ok.setText(R.string.cancel);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adviceDialog.cancel();
			}
		});
		// ����Դ
		list = getListForMoreAdapter();

		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.dialog_advice_list_item, new String[] { TITLE },
				new int[] { R.id.dialog_advice_item_text_title });

		avdiceList.setAdapter(adapter);
		adviceDialog.getWindow().setContentView(v);
	}

	/**
	 * ��ϵ��ѡ���
	 */
	public void LinkDialog() {
		isDialog = true;
		Cursor c = getContentResolver().query(People.CONTENT_URI, null, null,
				null, null);
        persons.clear();
		while (c.moveToNext()) {
			int index = c.getColumnIndex(People.NUMBER);
			int indexName = c.getColumnIndex(People.NAME);
			Person person = new Person(c.getString(indexName), c
					.getString(index));
			if(person.phone!=null){
				persons.add(person);
			}
		}
		startManagingCursor(c);
		checkedState = new boolean[persons.size()];

		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dialog_advice, null);
		TextView title = (TextView) v.findViewById(R.id.alert_dialog_touzhu_text_title);
		title.setText(R.string.ruyichuanqing_phonetext);

		final AlertDialog adviceDialog = new AlertDialog.Builder(this).create();

		adviceDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub;
				phoneStr = "";
				for (int i = 0; i < checkedPersons.size(); i++) {
					phoneStr += checkedPersons.get(i).phone;
					if (i != checkedPersons.size() - 1) {
						phoneStr += ",";
					}
				}

				editPhone.setText(phoneStr);
			}
		});
		avdiceList = (ListView) v.findViewById(R.id.dialog_advice_list);
		avdiceList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				adviceDialog.cancel();
				editPhone.setText(persons.get(position).phone);

			}
		});

		// ����Դ
		list = getListForLinkAdapter(persons);
		LinkAdapter adapter = new LinkAdapter(this, list);
		avdiceList.setItemsCanFocus(false);
		avdiceList.setAdapter(adapter);

	    Button ok = (Button) v.findViewById(R.id.dialog_advice_img_ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				adviceDialog.cancel();
				dialogOk();
			}
		});

		if (persons.size() == 0) {
			Toast.makeText(this, R.string.ruyichuanqing_thecontactsisnull, Toast.LENGTH_SHORT).show();
		} else {
//			adviceDialog.show();
//			adviceDialog.getWindow().setContentView(v);
			setContentView(v);
		}
       final EditText checkEdit = (EditText) v.findViewById(R.id.dialog_advice_edit_check);
       checkEdit.setVisibility(EditText.VISIBLE);
       checkEdit.addTextChangedListener(new TextWatcher() {
           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
                   // TODO Auto-generated method stub
           }
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count,
                           int after) {

           }
    	   @Override
		   public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
    		Vector<Person> newPersons = new Vector<Person>();// ������ϵ��
			String nameStr = checkEdit.getText().toString();
			if(nameStr!=null&&!nameStr.equals("")){
				for(int i=0;i<persons.size();i++){
					if(persons.get(i).name!=null&&persons.get(i).name.indexOf(nameStr)!=-1){
						persons.get(i).checkName = nameStr;
						newPersons.add(persons.get(i));
					}
				}
				// ����Դ
				list = getListForLinkAdapter(newPersons);
				LinkAdapter adapter = new LinkAdapter(GiftActivity.this, list);
				avdiceList.setItemsCanFocus(false);
				avdiceList.setAdapter(adapter);	
			}else{
				for(int i=0;i<persons.size();i++){
				  persons.get(i).checkName = nameStr;
				}
				// ����Դ
				list = getListForLinkAdapter(persons);
				LinkAdapter adapter = new LinkAdapter(GiftActivity.this, list);
				avdiceList.setItemsCanFocus(false);
				avdiceList.setAdapter(adapter);	
			}
		   }
        });
		
	}
	/**
	 * �绰��ȷ��
	 */
	public void dialogOk(){
		setContentView(R.layout.layout_gift);
		init();
		getInfo();
		setValue();
		phoneStr = "";
		for (int i = 0; i < checkedPersons.size(); i++) {
			phoneStr += checkedPersons.get(i).phone;
			if (i != checkedPersons.size() - 1) {
				phoneStr += ",";
			}
		}

		editPhone.setText(phoneStr);
	}

	/**
	 * �б�������������Դ
	 * 
	 * @return
	 */
	protected List<Map<String, Object>> getListForMoreAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < Constants.avdiceStr.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, Constants.avdiceStr[i]);
			list.add(map);

		}

		return list;
	}

	/**
	 * ��ϵ���б�������������Դ
	 * 
	 * @return
	 */
	protected List<Map<String, Object>> getListForLinkAdapter(Vector<Person> persons) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < persons.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(INFO, persons.get(i));
			list.add(map);

		}

		return list;
	}

	/**
	 * ��ϵ�˵�������
	 */
	public class LinkAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �������б���
		private List<Map<String, Object>> mList;

		public LinkAdapter(Context context, List<Map<String, Object>> list) {
			mInflater = LayoutInflater.from(context);
			mList = list;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			final int index = position;
			ViewHolder holder = null;
			final Person person= (Person) mList.get(position).get(INFO);
			// if (convertView == null) {
			convertView = mInflater.inflate(R.layout.dialog_link_list_item,
					null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.dialog_link_item_text_title);
			holder.phone = (TextView) convertView
					.findViewById(R.id.dialog_link_item_text_phone);
			holder.check = (CheckBox) convertView
					.findViewById(R.id.dialog_link_item_check);
			convertView.setTag(holder);

			holder.check.setChecked(person.isChecked);
			if(person.name!=null){
				setResultColor(person.checkName,person.name,holder.title);
			}
			if(person.phone!=null){
				holder.phone.setText(person.phone);
			}
			holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							person.isChecked = isChecked;
							if (isChecked) {
								checkedPersons.add(person);
							} else {
								checkedPersons.remove(person);
							}
						}
					});
			return convertView;
		}

		class ViewHolder {
			TextView title;
			TextView phone;
			CheckBox check;
		}
	}
    
	/**	
	 * ������ɫ
	 */
	public void setResultColor(String checkName, String name, TextView result) {
		SpannableStringBuilder builder = new SpannableStringBuilder();
		int start = name.indexOf(checkName);
		int max = name.length();
		endMax = restrictMax;
		for(int i=0;i<restrictMax;i++){ //ѭ�������ַ���
			if(Pattern.compile("(?i)[a-z]").matcher(name).find()||Pattern.compile("(?i)[A-Z]").matcher(name).find()){ //��char��װ���е��ж����ֵķ����ж�ÿһ���ַ� 
				endMax++;
			}
		}
		if(max>endMax){
			name = name.substring(0, endMax);
			for(int i=0;i<max-endMax;i++){
				name+=".";
			}
		}
		builder.append(name);
		if(!checkName.equals("")){
			builder.setSpan(new ForegroundColorSpan(Color.RED), start,start+checkName.length(), Spanned.SPAN_COMPOSING);
		}
		result.setText(builder, BufferType.EDITABLE);
	}
	/**
	 * Ͷע��ʾ��
	 * 
	 * @param title
	 * @param string
	 */
	public void touDialog(String title, String string) {
          TouDialog touDialog = new TouDialog(this, title, string);
          touDialog.showDialog();
          touDialog.createMyDialog();
          
	}
	class TouDialog extends BaseDialog{

		public TouDialog(Activity activity, String title, String message) {
			super(activity, title, message);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onOkButton() {
			// TODO Auto-generated method stub
			giftNet();
		}

		@Override
		public void onCancelButton() {
			// TODO Auto-generated method stub
			
		}
		
	}

	/**
	 * ���ʽ����ʾ��
	 * 
	 * @param title
	 * @param string
	 */
	public void resultDialog(String title, String string) {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.gift_result_dialog_view, null);
		TextView content = (TextView) v
				.findViewById(R.id.gift_result_view_text);
		content.setText(string);
		Dialog dialog = new AlertDialog.Builder(this).setView(v).setCancelable(false)
				.setTitle(title).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// buyImplement.touzhuNet();
								checkedPersons.clear();
								editPhone.setText("");
								if(successPersons.size()>0){
									sendSMS();
								}
							}
						}).create();
		dialog.show();
	}

	public String resultAlertStr() {
		String success = "", fail = "", result = "";
		try {
			JSONObject json = obj.getJSONObject("gift_result");
			success = json.getString("success");
			fail = json.getString("fail");
			result += "��������\n";
			if (!success.equals("")) {
				result += getName(success,true);
				result += "���ͳɹ�\n\n";
			}
			if (!fail.equals("")) {
				result += getName(fail,false);
				result += "����ʧ��\n";
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getName(String str,boolean isSuccess) {
		String result = "";
		String sPersons[] = str.split(",");
		for (int i = 0; i < sPersons.length; i++) {
			for (int j = 0; j < checkedPersons.size(); j++) {
				if (sPersons[i].equals(checkedPersons.get(j).phone)) {
					String name = checkedPersons.get(j).name;
					if(name == null){
						name = "";
					}
					result += name;
					for (int n = 0; n < 7 - name.length(); n++) {
						result += "---";
					}
					result += checkedPersons.get(j).phone + "\n";
					if(isSuccess){
                     successPersons.add(checkedPersons.get(j).phone);						
					}
					break;
				} else if (j == checkedPersons.size() - 1) {
					for (int n = 0; n < 7; n++) {
						result += "---";
					}
					result += sPersons[i] + "\n";
					if(isSuccess){
	                 successPersons.add(sPersons[i]);						
				    }
				}
			}
		}
		return result;
	}	
	/**
	 * �����ŷ���
	 * 
	 */
	private void sendSMS(){
		new Thread() {
			public void run() {
				boolean sendOk = true;
				for (int i = 0; i < successPersons.size(); i++) {
					String content = null;
			            if(betAndGift.getLotno().equals(Constants.LOTNO_JC)||betAndGift.getLotno().equals(Constants.LOTNO_JCLQ)
			            		||betAndGift.getLotno().equals(Constants.LOTNO_JCLQ_RF)||betAndGift.getLotno().equals(Constants.LOTNO_JCLQ_SFC)
			            		||betAndGift.getLotno().equals(Constants.LOTNO_JCLQ_DXF)){
			            	content = "���ĺ���" + betAndGift.getPhonenum() + "����"
							+ PublicMethod.toLotno(betAndGift.getLotno())+" ��Ʊ"
							+ zhu + "ע,"
							+ zhumaStr
							+ "�������£�"
							+ betAndGift.getAdvice()+" ������¼����ƽ̨��ȡ��Ʊ����ѯwww.ruyicai.com";
			            }else{
			            	content = "���ĺ���" + betAndGift.getPhonenum() + "����"
							+ PublicMethod.toLotno(betAndGift.getLotno()) +"  ��"
							+ PublicMethod.toIssue(betAndGift.getLotno()) + "�ڲ�Ʊ"
							+ zhu + "ע,"
							+ zhumaStr
							+ "�������£�"
							+ betAndGift.getAdvice()+" ������¼����ƽ̨��ȡ��Ʊ����ѯwww.ruyicai.com";
			            }
					String code = successPersons.get(i);
					sendOk = PublicMethod.sendSMS(code, content);// (String)iNumbers.elementAt(i));//
					if (sendOk == false) {
						break;
					}
				}
				if (sendOk) {
					handlerTwo.post(new Runnable() {
						public void run() {
						  Toast.makeText(GiftActivity.this, "�ѷ��Ͷ��Ÿ�֪���ѣ�", Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					handlerTwo.post(new Runnable() {
						public void run() {
						   Toast.makeText(GiftActivity.this, "���Ͷ���ʧ�ܣ�", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}.start();
	}
	/**
	 * ��ȡ��ʾ��
	 * 
	 */
	public void giftMsgNet() {
        isMsg = true;
		showDialog(0); // ��ʾ������ʾ�� 2010/7/4
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
				str = GiftMessageInterface.giftMsg();
				try {
					obj = new JSONObject(str);
					giftMsg = obj.getString("message");
					String error = obj.getString("error_code");
					handler.handleMsg(error, "");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				progressdialog.dismiss();
			}

		});
		t.start();
	}

	/**
	 * ���Ͳ�Ʊ����
	 * 
	 */
	public void giftNet() {
		isMsg = false;
		betAndGift.setBettype("gift");
		betAndGift.setAmount(PublicMethod.toFen(Integer.toString(allAtm)));
		betAndGift.setTo_mobile_code(editPhone.getText().toString().replace("��", ","));
		betAndGift.setAdvice(editLanguage.getText().toString());
		showDialog(0); // ��ʾ������ʾ�� 2010/7/4
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";

			@Override
			public void run() {
				str = BetAndGiftInterface.getInstance().betOrGift(betAndGift);
				try {
					obj = new JSONObject(str);
					message = obj.getString("message");
					String error = obj.getString("error_code");
					handler.handleMsg(error, message);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				progressdialog.dismiss();
			}

		});
		t.start();
	}

	/**
	 * Ͷע����
	 */
	private void beginTouZhu() {
		String alertStr = "����Ҫ����" + checkedPersons.size() + "λ���ѣ����ͽ��Ϊ"
				+ allAtm * checkedPersons.size() + "Ԫ��" + "�Ƿ����ͣ�";
		String phone = editPhone.getText().toString();
		if(isNum(phone)){
			touDialog("ȷ��Ҫ������", alertStr);
		}
		

	}
	public boolean isNum(String str){
		for(int i=0;i<checkedPersons.size();i++){
			String phone = checkedPersons.get(i).phone;
			if(!PublicMethod.isphonenum(phone)){
				Toast.makeText(this, "��������ֻ���"+phone+"�Ǵ���ģ����������룡", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		return true;
	}

	/**
	 * ����������ʾ��
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			progressdialog = new ProgressDialog(this);
			// progressdialog.setTitle("Indeterminate");
			progressdialog.setMessage("����������...");
			progressdialog.setIndeterminate(true);
			progressdialog.setCancelable(true);
			return progressdialog;
		}
		}
		return null;
	}

	public void errorCode_0000() {
		// TODO Auto-generated method stub
		if(isMsg){
			Constants.avdiceStr = giftMsg.split("\\|");
			adviceDialog();
		}else{
			resultDialog("���ͽ��", resultAlertStr());
			
		}
	}

	public void errorCode_000000() {
		// TODO Auto-generated method stub

	}

	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}

	class Person {
		public String name="";
		public String phone="";
		public String checkName="";
		public boolean isChecked=false;

		public Person(String name, String phone) {
			this.name = name;
			this.phone = phone;
		}
		public Person(){
			
		}
	}
	/**
	 * ��д�Żؽ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case 4:
			if(isDialog){
				dialogOk();
			}else{
				finish();
			}
			break;
		}
		return false;
	}
}
