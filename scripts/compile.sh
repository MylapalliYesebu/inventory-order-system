#!/usr/bin/env bash
set -euo pipefail

javac -cp lib/sqlite-jdbc-3.36.0.3.jar -d out \
  src/com/yesebu/inventory/db/DatabaseManager.java \
  src/com/yesebu/inventory/model/Product.java \
  src/com/yesebu/inventory/model/Order.java \
  src/com/yesebu/inventory/dao/ProductDAO.java \
  src/com/yesebu/inventory/dao/OrderDAO.java \
  src/com/yesebu/inventory/service/InventoryService.java \
  src/com/yesebu/inventory/app/InventoryApp.java
