# 基于 GIS 的成都市高校分布式图书管理系统（含统一 DTO、JTS 映射、图书检索与借还 API）

## 运行步骤
1) 在 Oracle 中按顺序执行：
   - db/oracle/01_schema.sql
   - db/oracle/02_metadata_and_index.sql
   - db/oracle/03_seed.sql
   - db/oracle/04_borrow_seed.sql
2) 修改 `backend/src/main/resources/application.properties` 的连接信息；
3) 启动：
```bash
cd backend
mvn spring-boot:run
```
访问 http://localhost:8080/

## API 列表
- `GET /api/libraries`：所有图书馆（含 lon/lat）
- `GET /api/libraries/nearby?lat=..&lon=..&radiusKm=2`：范围查询（SDO_WITHIN_DISTANCE）
- `GET /api/libraries/nearest?lat=..&lon=..&k=3`：最近邻（SDO_NN）
- `GET /api/books/search?q=关键词`：**统一图书检索**（书目 + 馆藏 + 馆点）
- `POST /api/borrow`：**跨校借书**（入参：`userId, inventoryId, borrowLibId`；创建 ACTIVE 借阅单，库存置 BORROWED）
- `POST /api/return`：**异地还书**（入参：`recordId, returnLibId`；记录置 RETURNED，库存在归还馆置 AVAILABLE → 采用“流动藏”策略）
- `GET /api/borrows/user/{userId}`：按用户查看借阅记录
