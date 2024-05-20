import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatasetTherapy } from 'app/shared/model/dataset-therapy.model';
import { DatasetTherapyService } from './dataset-therapy.service';

@Component({
  selector: 'jhi-dataset-therapy-delete-dialog',
  templateUrl: './dataset-therapy-delete-dialog.component.html',
})
export class DatasetTherapyDeleteDialogComponent {
  datasetTherapy: IDatasetTherapy;

  constructor(
    protected datasetTherapyService: DatasetTherapyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.datasetTherapyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'datasetTherapyListModification',
        content: 'Deleted an datasetTherapy',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dataset-therapy-delete-popup',
  template: '',
})
export class DatasetTherapyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetTherapy }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DatasetTherapyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.datasetTherapy = datasetTherapy;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dataset-therapy', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dataset-therapy', { outlets: { popup: null } }]);
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
