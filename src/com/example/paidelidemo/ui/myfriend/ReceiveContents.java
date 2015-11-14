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

	// ��ȡ��ϵ�˵��������绰��Ϣ
	public List<ContactInfo> getContact()
	{
		// ȡ��ContentResolver
		ContentResolver content =context.getContentResolver();
		Uri uri = ContactsContract.Contacts.CONTENT_URI; // ��ϵ�˵�URI
		Cursor cursor = content.query(uri, null, null, null, null);
		int contactCount = cursor.getCount(); // �����ϵ����Ŀ

		List<ContactInfo> contacts = new ArrayList<ContactInfo>(contactCount);

		if (cursor.moveToFirst())
		{
			// ѭ������
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

				// �����ϵ�˵�ID��
				String contactId = cursor.getString(idColumn);
				contact.setContactId(contactId);
				// �����ϵ������
				String disPlayName = cursor.getString(displayNameColumn);
				contact.setContactName(disPlayName);

				// �绰����ĸ���
				// String phoneString = cursor.getString(phoneColumn);
				int phoneNum = cursor.getInt(phoneColumn);

				if (phoneNum > 0)
				{
					// �����ϵ�˵ĵ绰�����cursor;
					Cursor phones = content.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " 
					+ contactId, null, null);

					int phoneCount = phones.getCount();
					allPhoneNum = new ArrayList<String>(phoneCount);
					if (phones.moveToFirst())
					{
						// �������еĵ绰����
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
