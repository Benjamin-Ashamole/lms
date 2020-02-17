package com.benjamin.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.benjamin.lms.pojo.Author;
import com.benjamin.lms.pojo.Branch;

public class BranchDAO extends BaseDAO{

	public BranchDAO(Connection conn) {
		super(conn);
		
	}
	
	public void addBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("insert into tbl_library_branch (branchName, branchAddress) values (?, ?)", new Object[] { branch.getBranchName(), branch.getBranchAddress() });
	}
	
	public void updateBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("update tbl_library_branch set branchName" + "=?, branchAddress"+" =? where branchId = ?", new Object[] {branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()});
	}
	
	public void deleteBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("delete from tbl_library_branch where branchId = ?", new Object[] {branch.getBranchId()});
	}
	
	
	public List<Branch> readBranches() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_library_branch", null);
	}
	
	
	@Override
	List extractData(ResultSet rs) throws SQLException {
		List<Branch> branches = new ArrayList<>();
		while(rs.next()){
			Branch br = new Branch();
			br.setBranchId(rs.getInt("branchId"));
			br.setBranchName(rs.getString("branchName"));
			br.setBranchAddress(rs.getString("branchAddress"));
			branches.add(br);
		}
		return branches;
	}

}
