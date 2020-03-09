package repository;

import domain.CategVarsta;
import utils.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class RepoCategVarsta implements Repo<CategVarsta> {
    ConnectionHelper connectionHelper;

    public  RepoCategVarsta(Properties props){
        connectionHelper=new ConnectionHelper(props);
    }
    @Override
    public void adauga(CategVarsta elem) throws Exception {

    }

    @Override
    public void sterge(Integer id) {

    }

    @Override
    public CategVarsta cauta(int id) {
        try(Connection con=connectionHelper.getConnection()){
            try (PreparedStatement selectStm=con.prepareStatement("select * from categvarsta where id=?")){
                selectStm.setInt(1,id);
                try(ResultSet rs=selectStm.executeQuery()){
                    rs.next();
                    return new CategVarsta(id,rs.getString("nume"),rs.getInt("varstaStart"),rs.getInt("varstaEnd"));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<CategVarsta> getAll() {
        ArrayList<CategVarsta> all = new ArrayList<>();
        try (Connection con = connectionHelper.getConnection()) {
            try (PreparedStatement selectStm = con.prepareStatement("select * from categvarsta")) {
                try (ResultSet rs = selectStm.executeQuery()) {
                    while (rs.next()) {
                        CategVarsta ct = new CategVarsta(rs.getInt("id"), rs.getString("nume"), rs.getInt("varstaStart"), rs.getInt("varstaEnd"));
                        all.add(ct);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }

    @Override
    public void modifica(CategVarsta elem) {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
