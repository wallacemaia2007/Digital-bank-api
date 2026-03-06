import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTable, MatTableDataSource, MatTableModule } from '@angular/material/table';
import { BooleanStatusPipe } from '../../pipes/boolean-status/boolean-status.pipe';

export type TableColumn = {
  label: string;
  key: string;
  type: 'text' | 'date' | 'currency' | 'status' | 'menu';
}

@Component({
  selector: 'app-commom-table',
  standalone: true,
  templateUrl: './commom-table.component.html',
  styleUrl: './commom-table.component.scss',
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatTableModule,
    MatSortModule,
    MatMenuModule,
    MatIconModule,
    BooleanStatusPipe,
  ],
})
export class CommomTableComponent<T> implements OnChanges, AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<T>;

  @Input({ required: true }) data!: T[];
  @Input({ required: true }) displayedColumns!: TableColumn[];

  @Output() detail = new EventEmitter<T>();
  @Output() edit = new EventEmitter<T>();
  @Output() delete = new EventEmitter<T>();

  public page = 1;
  public size = 10;
  public dataSource!: MatTableDataSource<T>;
  public displayedColumnsKeys!: string[];

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['data']) {
      this.dataSource = new MatTableDataSource(changes['data'].currentValue);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
    if (changes['displayedColumns']) {
      this.displayedColumnsKeys = changes['displayedColumns'].currentValue.map((column: TableColumn) => column.key);
    }
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.table.dataSource = this.dataSource;
  }

  pagination(event: PageEvent) {
    this.page = event.pageIndex + 1;
    this.size = event.pageSize;
  }

  detailClick(row: T) {
    this.detail.emit(row);
  }

  editClick(row: T) {
    this.edit.emit(row);
  }

  deleteClick(row: T) {
    this.delete.emit(row);
  }
}
