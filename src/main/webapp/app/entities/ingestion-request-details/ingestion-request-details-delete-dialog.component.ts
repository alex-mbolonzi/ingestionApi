import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';
import { IngestionRequestDetailsService } from './ingestion-request-details.service';

@Component({
  selector: 'jhi-ingestion-request-details-delete-dialog',
  templateUrl: './ingestion-request-details-delete-dialog.component.html',
})
export class IngestionRequestDetailsDeleteDialogComponent {
  ingestionRequestDetails: IIngestionRequestDetails;

  constructor(
    protected ingestionRequestDetailsService: IngestionRequestDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ingestionRequestDetailsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ingestionRequestDetailsListModification',
        content: 'Deleted an ingestionRequestDetails',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ingestion-request-details-delete-popup',
  template: '',
})
export class IngestionRequestDetailsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ingestionRequestDetails }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(IngestionRequestDetailsDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static',
        });
        this.ngbModalRef.componentInstance.ingestionRequestDetails = ingestionRequestDetails;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ingestion-request-details', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ingestion-request-details', { outlets: { popup: null } }]);
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
