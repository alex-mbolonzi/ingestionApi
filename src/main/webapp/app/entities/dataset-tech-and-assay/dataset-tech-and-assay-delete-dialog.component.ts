import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';
import { DatasetTechAndAssayService } from './dataset-tech-and-assay.service';

@Component({
  selector: 'jhi-dataset-tech-and-assay-delete-dialog',
  templateUrl: './dataset-tech-and-assay-delete-dialog.component.html',
})
export class DatasetTechAndAssayDeleteDialogComponent {
  datasetTechAndAssay: IDatasetTechAndAssay;

  constructor(
    protected datasetTechAndAssayService: DatasetTechAndAssayService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.datasetTechAndAssayService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'datasetTechAndAssayListModification',
        content: 'Deleted an datasetTechAndAssay',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dataset-tech-and-assay-delete-popup',
  template: '',
})
export class DatasetTechAndAssayDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetTechAndAssay }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DatasetTechAndAssayDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static',
        });
        this.ngbModalRef.componentInstance.datasetTechAndAssay = datasetTechAndAssay;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dataset-tech-and-assay', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dataset-tech-and-assay', { outlets: { popup: null } }]);
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
