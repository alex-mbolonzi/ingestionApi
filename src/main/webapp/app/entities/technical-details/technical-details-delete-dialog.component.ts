import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITechnicalDetails } from 'app/shared/model/technical-details.model';
import { TechnicalDetailsService } from './technical-details.service';

@Component({
  selector: 'jhi-technical-details-delete-dialog',
  templateUrl: './technical-details-delete-dialog.component.html',
})
export class TechnicalDetailsDeleteDialogComponent {
  technicalDetails: ITechnicalDetails;

  constructor(
    protected technicalDetailsService: TechnicalDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.technicalDetailsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'technicalDetailsListModification',
        content: 'Deleted an technicalDetails',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-technical-details-delete-popup',
  template: '',
})
export class TechnicalDetailsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ technicalDetails }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TechnicalDetailsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.technicalDetails = technicalDetails;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/technical-details', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/technical-details', { outlets: { popup: null } }]);
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
