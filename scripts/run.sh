#!/usr/bin/env bash
set -euo pipefail

java -cp out:lib/sqlite-jdbc-3.36.0.3.jar com.yesebu.inventory.app.InventoryApp
