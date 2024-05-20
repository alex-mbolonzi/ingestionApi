import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatasetIndication } from 'app/shared/model/dataset-indication.model';
import { DatasetIndicationService } from './dataset-indication.service';

@Component({
  selector: 'jhi-dataset-indication-delete-dialog',
  templateUrl: './dataset-indication-delete-dialog.component.html',
})
export class DatasetIndicationDeleteDialogComponent {
  datasetIndication: IDatasetIndication;

  constructor(
    protected datasetIndicationService: DatasetIndicationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.datasetIndicationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'datasetIndicationListModification',
        content: 'Deleted an datasetIndication',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dataset-indication-delete-popup',
  template: '',
})
export class DatasetIndicationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetIndication }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DatasetIndicationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.datasetIndication = datasetIndication;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dataset-indication', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dataset-indication', { outlets: { popup: null } }]);
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
