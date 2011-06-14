
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.zving.schema.ZCCatalogSchema;

public class SchemaTest {

	@Test
	public void insert() {
		ZCCatalogSchema schema = new ZCCatalogSchema();
		schema.setSiteID(206);
		schema.setParentID(0);
		schema.setID(8087);
		schema.setName("sky");
		schema.setInnerCode("darkness");
		schema.setAlias("说明");
		schema.setType(10);
		schema.setTreeLevel(0);
		schema.setChildCount(0);
		schema.setIsLeaf(0);
		schema.setTotal(0);
		schema.setOrderFlag(0);
		schema.setPublishFlag("f");
		schema.setAddUser("darkness");
		schema.setAddTime(new Date());
		schema.insert();
		Assert.assertTrue(schema.getSiteID() == 206 && schema.getID() == 8087);
	}

	@Test
	public void fill() {
		ZCCatalogSchema schema = new ZCCatalogSchema();
		schema.setID(8087);
		schema.fill();
		Assert.assertTrue(schema.getSiteID() == 206);
	}

	@Test
	public void update() {
		ZCCatalogSchema schema = new ZCCatalogSchema();
		schema.setID(8087);
		schema.fill();
		schema.setAddTime(new Date());
		schema.setName("未知幻影");
		schema.update();
		Assert.assertTrue(schema.getSiteID() == 206 && schema.getID() == 8087
				&& schema.getName().equals("未知幻影"));
	}

	@Test
	public void querySet() {
		ZCCatalogSchema schema = new ZCCatalogSchema();
		int size = schema.query().size();
		Assert.assertTrue(size == 1);
	}
}
