package com.example.paidelidemo.ui.myfriend;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ReceiveContents 
{
	Context context;
	public ReceiveContents(Context context)
	{
		this.context = context;
	}

	// 获取联系人的姓名、电话信息
	public List<ContactInfo> getContact()
	{
		// 取得ContentResolver
		ContentResolver content =context.getContentResolver();
		Uri uri = ContactsContract.Contacts.CONTENT_URI; // 联系人的URI
		Cursor cursor = content.query(uri, null, null, null, null);
		int contactCount = cursor.getCount(); // 获得联系人数目

		List<ContactInfo> contacts = new ArrayList<ContactInfo>(contactCount);

		if (cursor.moveToFirst())
		{
			// 循环遍历
			for (; !cursor.isAfterLast(); cursor.moveToNext())
			{
				List<String> allPhoneNum = null;
				
				ContactInfo contact = new ContactInfo();

				int idColumn = cursor
						.getColumnIndex(ContactsContract.Contacts._ID);
				int displayNameColumn = cursor
						.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
				int phoneColumn = cursor
						.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

				// 获得联系人的ID号
				String contactId = cursor.getString(idColumn);
				contact.setContactId(contactId);
				// 获得联系人姓名
				String disPlayName = cursor.getString(displayNameColumn);
				contact.setContactName(disPlayName);

				// 电话号码的个数
				// String phoneString = cursor.getString(phoneColumn);
				int phoneNum = cursor.getInt(phoneColumn);

				if (phoneNum > 0)
				{
					// 获得联系人的电话号码的cursor;
					Cursor phones = content.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " 
					+ contactId, null, null);

					int phoneCount = phones.getCount();
					allPhoneNum = new ArrayList<String>(phoneCount);
					if (phones.moveToFirst())
					{
						// 遍历所有的电话号码
						for (; !phones.isAfterLast(); phones.moveToNext())
						{
							String phoneNumber = phones
									.getString(phones
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							allPhoneNum.add(phoneNumber);
						}

						if (!phones.isClosed())
						{
							phones.close();
						}
					}
				}

				contact.setContactPhone(allPhoneNum);
				contacts.add(contact);
			}

			if (!cursor.isClosed())
			{
				cursor.close();
			}
		}
		return contacts;

	}

	public class ContactInfo
	{
		private String contactId;
		private String contactName;
		private List<String> contactPhone;

		public String getContactId()
		{
			return contactId;
		}

		public String getContactName()
		{
			return contactName;
		}

		public List<String> getContactPhone()
		{
			return contactPhone;
		}

		public void setContactId(String id)
		{
			contactId = id;
		}

		public void setContactName(String name)
		{
			contactName = name;
		}

		public void setContactPhone(List<String> phone)
		{
			contactPhone = phone;
		}

	}
}
