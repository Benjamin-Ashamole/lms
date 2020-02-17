package com.benjamin.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Genre;
import com.benjamin.lms.pojo.Publisher;

public class PublisherDao extends BaseDAO {

	public PublisherDao(Connection conn) {
		super(conn);
		
	}
	
	public void addPublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?,?,?)", new Object[] {pub.getPublisherName(), pub.getPublisherAddress(), pub.getPublisherPhone()});
	}
	
	
	public Integer addPublisherPKR(Publisher pub) throws ClassNotFoundException, SQLException {
		return saveReturnPk("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?,?,?)", new Object[] {pub.getPublisherName(), pub.getPublisherAddress(), pub.getPublisherPhone()});
	}
	public void updatePublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		save("update tbl_publisher set publisherName =?, publisherAddress = ?, publisherPhone =? where publisherId = ?", new Object[] {pub.getPublisherName(), pub.getPublisherAddress(), pub.getPublisherPhone(), pub.getPublisherId()});
	}
	
	public void deletePublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		save("delete from tbl_publisher where publisherId = ?", new Object[] {pub.getPublisherId()});
	}
	
	public List<Publisher> readPublisher() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_publisher", null);
	}

	@Override
	List extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()){
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(p);
		}
		return publishers;
	}

}
