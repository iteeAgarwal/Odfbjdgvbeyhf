package com.dan.dqms.returnlist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dqms.db.Room;
import org.dqms.db.TokenGroup;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.token.TokenGeneratorData;
import java.sql.*;
public class RoomsList {
	
	
	PreparedStatement p1,p2,p3;
	ResultSet r1,r2,r3;
	Connection con;
	public List<Room> getRooms() {
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		List<Room> roomsList = new ArrayList<Room>();
        Query qry = session.createQuery("from Room p");

        try{
        
		List l = qry.list();
        Iterator it = l.iterator();

		while (it.hasNext()) {
			Object obc = (Object) it.next();
			Room roomBean = (Room) obc;

			roomBean.setRoom_id(roomBean.getRoom_id());
			roomBean.setRoom_no(roomBean.getRoom_no());
			roomBean.setDepart_id(roomBean.getDepart_id());
			roomBean.setLocation(roomBean.getLocation());
			roomBean.setWdu_id(roomBean.getWdu_id());
 System.out.println("All Room Id"+roomBean.getRoom_id());
			roomsList.add(roomBean);

		}
		 t.commit();
        }
        catch(Exception e)
        {
        	Print.logException("Exception in RoomsList class  ", e);
        }
        finally
        {
       
        	session.close();
            
          DBManager.closeFactory();
        }
		return roomsList;

	}

	public List<Room> getRoomsByID(String roomID) {
		
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		List<Room> roomsList = new ArrayList<Room>();
		try{
			
		
        Query qry = session.createQuery("from Room p where room_id = '"
				+ roomID + "'");

		List l = qry.list();

		Iterator it = l.iterator();

		while (it.hasNext()) {
			Object obc = (Object) it.next();
			Room roomBean = (Room) obc;

			roomBean.setRoom_id(roomBean.getRoom_id());
			roomBean.setRoom_no(roomBean.getRoom_no());
			roomBean.setDepart_id(roomBean.getDepart_id());
			roomBean.setLocation(roomBean.getLocation());
			roomBean.setWdu_id(roomBean.getWdu_id());

			roomsList.add(roomBean);

		}
		}
        catch(Exception e)
        {
        	Print.logException("Exception in RoomsList class  ", e);
        }
		t.commit();
		session.close();
        DBManager.closeFactory();
		return roomsList;

	}
	
	public List<Room> getRoomsBydepID(String depID) {
		
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		List<Room> roomsList = new ArrayList<Room>();

		try{
		Query qry = session.createQuery("from Room p where depart_id = '"
				+ depID + "'");

		List l = qry.list();

		Iterator it = l.iterator();

		while (it.hasNext()) {
			Object obc = (Object) it.next();
			Room roomBean = (Room) obc;

			roomBean.setRoom_id(roomBean.getRoom_id());
			roomBean.setRoom_no(roomBean.getRoom_no());
			roomBean.setDepart_id(roomBean.getDepart_id());
			roomBean.setLocation(roomBean.getLocation());
			roomBean.setWdu_id(roomBean.getWdu_id());

			roomsList.add(roomBean);

		}
		}
        catch(Exception e)
        {
        	Print.logException("Exception in RoomsList class  ", e);
        }
		
		t.commit();
		session.close();
		DBManager.closeFactory();
		return roomsList;

	}
	

	
	
	
	
	public List<Room> getUniqeRoomsBydepID(String depID) {
		PreparedStatement pstmt=null,pstmt2=null;
		ResultSet rs=null,rs2=null;
		TokenGeneratorData tk=new TokenGeneratorData();
		String roomArr[]=null;
		ArrayList<Integer> alRooms=new ArrayList<Integer>();
		Set<String> set=new HashSet<String>();
		ArrayList<Integer> alFinal=new ArrayList<Integer>();
		List<Room> roomsList = new ArrayList<Room>();
		try{
			con=tk.connection();
			p1=con.prepareStatement("select * from room_details where depart_id=?");
			int iDepID=Integer.parseInt(depID);
			p1.setInt(1, iDepID);
			r1=p1.executeQuery();
			while(r1.next()){
				int room_id=r1.getInt("room_id");
				alRooms.add(room_id);
			}
			
			p2=con.prepareStatement("select * from token_group_details where Department_id=?");
			int iDepID2=Integer.parseInt(depID);
			p2.setInt(1, iDepID2);
			r2=p2.executeQuery();
			while(r2.next()){
				String _rooms=r2.getString("room_id");
				if(_rooms.contains(",")){
					roomArr=_rooms.split(",");
					for(String s:roomArr){set.add(s);}
				}
				else{
					set.add(_rooms);
				}
			}
			
			for(int i:alRooms){
				boolean flag=false;
				for(String s:set){
					if(i==Integer.parseInt(s)){
						flag=true;
					}
				}
				if(!flag){
					alFinal.add(i);
				}
			}
			
			if(alFinal.size()>0){
				for(int i:alFinal){
				p3=con.prepareStatement("select * from room_details where room_id=?");
				p3.setInt(1, i);
				r3=p3.executeQuery();
				while(r3.next()){
					Room roomBean=new Room();
					String _roomNo=r3.getString("room_no");
					int _wduID=r3.getInt("wdu_id");
					int _departID=r3.getInt("depart_id");
					String _loc=r3.getString("location");
					roomBean.setRoom_id(i);
					roomBean.setRoom_no(_roomNo);
					roomBean.setWdu_id(_wduID);
					roomBean.setDepart_id(_departID);
					roomBean.setLocation(_loc);
					roomsList.add(roomBean);
				}
				}
			}
			
		}
		catch(Exception e)
		{
			Print.logException("Exception " ,e);

		}
		finally{
			try{
			if(p1!=null)
			p1.close();
			if(p2!=null)
				p2.close();
			if(p3!=null)
				p3.close();
			if(r1!=null)
				r1.close();
			if(r2!=null)
				r2.close();
			if(r3!=null)
				r3.close();
			if(con!=null)
				con.close();
			}catch(Exception ex){}
		}

return roomsList;


	}
	
	
	
	
	
	}
