import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatasetDataCategory } from 'app/shared/model/dataset-data-category.model';
import { DatasetDataCategoryService } from './dataset-data-category.service';

@Component({
  selector: 'jhi-dataset-data-category-delete-dialog',
  templateUrl: './dataset-data-category-delete-dialog.component.html',
})
export class DatasetDataCategoryDeleteDialogComponent {
  datasetDataCategory: IDatasetDataCategory;

  constructor(
    protected datasetDataCategoryService: DatasetDataCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.datasetDataCategoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'datasetDataCategoryListModification',
        content: 'Deleted an datasetDataCategory',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dataset-data-category-delete-popup',
  template: '',
})
export class DatasetDataCategoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetDataCategory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DatasetDataCategoryDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static',
        });
        this.ngbModalRef.componentInstance.datasetDataCategory = datasetDataCategory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dataset-data-category', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dataset-data-category', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
