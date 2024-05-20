import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from './dataset-details.service';

@Component({
  selector: 'jhi-dataset-details-delete-dialog',
  templateUrl: './dataset-details-delete-dialog.component.html',
})
export class DatasetDetailsDeleteDialogComponent {
  datasetDetails: IDatasetDetails;

  constructor(
    protected datasetDetailsService: DatasetDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.datasetDetailsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'datasetDetailsListModification',
        content: 'Deleted an datasetDetails',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dataset-details-delete-popup',
  template: '',
})
export class DatasetDetailsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetDetails }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DatasetDetailsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.datasetDetails = datasetDetails;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dataset-details', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dataset-details', { outlets: { popup: null } }]);
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
